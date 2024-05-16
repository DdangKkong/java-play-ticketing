package zerobase18.playticketing.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerobase18.playticketing.review.dto.CreateReview;
import zerobase18.playticketing.review.dto.ReviewDto;
import zerobase18.playticketing.review.dto.ReviewList;
import zerobase18.playticketing.review.dto.ReviewUpdate;

public interface ReviewService {


    /**
     * 리뷰 작성
     */
    ReviewDto createReview(Integer customerId, Integer reservationId, CreateReview.Request request);


    /**
     * 리뷰 상세 조회
     */
    ReviewDto detailReview(Integer customerId, Integer reviewId);

    /**
     * 리뷰 전체 목록 조회
     */
    Page<ReviewList> viewReview(Pageable pageable);


    /**
     * 리뷰 검색 기능
     */
    Slice<ReviewDto> searchReview(String title, Pageable pageable);

    /**
     * 리뷰 수정
     */
    ReviewDto updateReview(Integer customerId, Integer reviewId, ReviewUpdate.Request request);


    /**
     * 리뷰 삭제
     */
    void deleteReview(Integer customerId, Integer reviewId);

    /**
     * 좋아요
     */
    void likePost(Integer customerId, Integer reviewId);


}
