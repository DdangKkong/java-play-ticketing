package zerobase18.playticketing.review.service.impl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.repository.ReservationRepository;
import zerobase18.playticketing.review.dto.CreateReview;
import zerobase18.playticketing.review.dto.ReviewDto;
import zerobase18.playticketing.review.dto.ReviewList;
import zerobase18.playticketing.review.dto.ReviewUpdate;
import zerobase18.playticketing.review.entity.Like;
import zerobase18.playticketing.review.entity.Review;
import zerobase18.playticketing.review.repository.CommentRepository;
import zerobase18.playticketing.review.repository.LikeRepository;
import zerobase18.playticketing.review.repository.ReviewRepository;
import zerobase18.playticketing.review.service.ReviewService;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static zerobase18.playticketing.global.type.ErrorCode.*;
import static zerobase18.playticketing.payment.type.ReserStat.SUCCESS;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReservationRepository reservationRepository;

    private final CustomerRepository customerRepository;

    private final CommentRepository commentRepository;

    private final LikeRepository likeRepository;

    private final RedisTemplate<String, Object> redisTemplate;


    private static final String VIEWED_REVIEWS_KEY_PREFIX = "viewed_reviews:";




    /**
     * 리뷰 작성
     */
    @Override
    @Transactional
    public ReviewDto createReview(Integer customerId, Integer reservationId, CreateReview.Request request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        validationReviewStatus(customer, reservation);

        Review save = reviewRepository.save(Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .customer(customer)
                .reservation(reservation)
                .rating(request.getRating())
                .build());

        return ReviewDto.fromEntity(save);
    }

    /**
     * 리뷰 상세 조회
     */
    @Override
    @Transactional
    public ReviewDto detailReview(Integer customerId, Integer reviewId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));



        // 상세 조회 시 조회수 레디스에 저장
        if (!isReviewAlreadyViewed(customer.getCustomerId(), review.getReviewId())) {
            addViewCountToRedis(reviewId);
            markReviewAsViewed(customerId, reviewId);
        }

        return ReviewDto.fromEntity(review);
    }

    /**
     * 리뷰 전체 목록 조회
     */
    @Override
    @Transactional
    public Page<ReviewList> viewReview(Pageable pageable) {

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), 10,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Review> reviews = reviewRepository.findAll(sortedPageable);

        // 레디스 조회수
        for (Review review : reviews.getContent()) {
            Long viewCount = getViewCountFromRedis(review.getReviewId());
            review.setViewCount((long) (viewCount != null ? viewCount.intValue() : 0));
        }

        return ReviewList.ReviewLists(reviews);
    }

    /**
     * 리뷰 검색
     */
    @Override
    @Transactional
    public Slice<ReviewDto> searchReview(String title, Pageable pageable) {
        return reviewRepository.searchByTitle(title, pageable).map(ReviewDto::fromEntity);
    }



    /**
     * 리뷰 수정
     */
    @Override
    @Transactional
    public ReviewDto updateReview(Integer customerId, Integer reviewId, ReviewUpdate.Request request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));

        if (!review.getCustomer().equals(customer)) {
            throw new CustomException(REVIEW_NOT_AVAILABLE);
        }

        // 리뷰 수정 시 입력을 안하면 이전 제목과 내용이 저장.
        String title = Optional.ofNullable(request.getTitle()).orElse(review.getTitle());

        String content = Optional.ofNullable(request.getContent()).orElse(review.getContent());

        review.setTitle(title);
        review.setContent(content);
        review.setRating(request.getRating());



        return ReviewDto.fromEntity(review);
    }


    /**
     * 리뷰 삭제
     */
    @Override
    @Transactional
    public void deleteReview(Integer customerId, Integer reviewId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));


        if (!Objects.equals(customer.getCustomerId(), review.getCustomer().getCustomerId())) {
            throw new CustomException(CUSTOMER_AUTHORITY_NOT_MATCH);
        }


        // 레디스에서 삭제하는 메서드 호출
        deleteFromRedis(reviewId);

        // 이미 조회한 리뷰 정보도 삭제
        removeAllFromViewedRedis(reviewId);

        likeRepository.deleteAllByReview(review);

        if (review.getComments().isEmpty()) {
            reviewRepository.delete(review);
        } else {
            commentRepository.deleteAll(review.getComments());
            reviewRepository.delete(review);
        }

    }

    /**
     * 좋아요
     */
    @Override
    @Transactional
    public void likePost(Integer customerId, Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        Like like = likeRepository.findByCustomerAndReview(customer, review);

        if (like == null) {
            // 좋아요가 없는 경우 새로 생성.
            like = new Like();
            like.setCustomer(customer);
            like.setReview(review);
            like.setIsLike(true); // 좋아요를 누름
            likeRepository.save(like);

            // Review 엔티티에 좋아요 추가 및 갯수 업데이트
            review.addLike(like);
            reviewRepository.save(review);
        } else {
            // 좋아요가 이미 누른 경우, 다시 요청 시 좋아요를 취소.
            likeRepository.delete(like);

            // Review 엔티티에서 좋아요 제거 및 갯수 업데이트
            review.getLikes().remove(like);
            review.updateLikeCount();
            reviewRepository.save(review);
        }


    }


    /**
     * 리뷰 작성 유효성 검사
     */
    private void validationReviewStatus(Customer customer, Reservation reservation) {

        if (!reservation.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            throw new CustomException(CUSTOMER_AUTHORITY_NOT_MATCH);
        }
        // 리뷰 작성 갯수는 1개로 제한
        if (reviewRepository.existsByReservationReserId(reservation.getReserId())) {
            throw new CustomException(ALREADY_EXIST_REVIEW);
        }
        // 예약 완료 시에만 리뷰 작성 가능
        if (!reservation.getReserStat().equals(SUCCESS)) {
            throw new CustomException(REVIEW_NOT_AVAILABLE);
        }

    }




    private void addViewCountToRedis(Integer reviewId) {
        String key = "reviewId::" + reviewId;
        Long viewCount = redisTemplate.opsForValue().increment(key, 1L); // 증가시키고 증가된 값 반환
        // 만약 해당 키가 존재하지 않는다면 초기값 1로 설정
        if (viewCount == null) {
            redisTemplate.opsForValue().set(key, 0);
        }
    }

    private Long getViewCountFromRedis(Integer reviewId) {
        String key = "reviewId::" + reviewId;
        return redisTemplate.opsForValue().increment(key, 0L); // 0L을 전달하여 현재 값을 조회만하고 증가시키지 않음
    }

    // 리뷰를 이미 조회한 것으로 표시하여 Redis에 저장
    private void markReviewAsViewed(Integer customerId, Integer reviewId) {
        String key = VIEWED_REVIEWS_KEY_PREFIX + customerId;
        redisTemplate.opsForSet().add(key, String.valueOf(reviewId));
    }

    // 이미 조회한 리뷰인지 확인
    private boolean isReviewAlreadyViewed(Integer customerId, Integer reviewId) {
        String key = VIEWED_REVIEWS_KEY_PREFIX + customerId;
        return redisTemplate.opsForSet().isMember(key, String.valueOf(reviewId));
    }

    // 레디스에서 리뷰 정보 삭제
    private void deleteFromRedis(Integer reviewId) {
        String key = "reviewId::" + reviewId;
        redisTemplate.delete(key);
    }

    // 레디스에서 이미 조회한 고객 정보 삭제
    private void removeAllFromViewedRedis(Integer reviewId) {
        // reviewId를 포함하는 Redis 집합의 키를 가져옴
        String keyPattern = VIEWED_REVIEWS_KEY_PREFIX + "*";
        Set<String> keys = redisTemplate.keys(keyPattern);

        // 각 키에 대해 reviewId를 포함하는 집합에서 모든 항목을 삭제.
        for (String key : keys) {
            if (redisTemplate.opsForSet().isMember(key, String.valueOf(reviewId))) {
                redisTemplate.delete(key);
            }
        }
    }



}
