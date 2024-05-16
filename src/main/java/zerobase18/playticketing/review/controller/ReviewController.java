package zerobase18.playticketing.review.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.review.dto.CreateReview;
import zerobase18.playticketing.review.dto.ReviewDetail;
import zerobase18.playticketing.review.dto.ReviewList;
import zerobase18.playticketing.review.dto.ReviewUpdate;
import zerobase18.playticketing.review.service.ReviewService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public CreateReview.Response createReview(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer reservationId,
            @RequestBody CreateReview.Request request
    ) {

        return CreateReview.Response.from(
                reviewService.createReview(customerId, reservationId, request)
        );
    }


    /**
     * 리뷰 상세 조회
     */
    @GetMapping("/detail")
    public ReviewDetail.Response detailReview(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer reviewId

    ) {
        return ReviewDetail.Response.fromEntity(
                reviewService.detailReview(customerId, reviewId)
        );
    }

    /**
     * 리뷰 전체 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<Page<ReviewList>> viewReview(Pageable pageable) {

        Page<ReviewList> reviewLists = reviewService.viewReview(pageable);

        return ResponseEntity.ok(reviewLists);

    }
    /**
     * 리뷰 검색
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchTitle(@RequestParam("title") String title,
                                         @PageableDefault(sort="createdAt", direction= Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reviewService.searchReview(title, pageable));
    }


    /**
     * 리뷰 수정
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ReviewUpdate.Response updateReview(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer reviewId,
            @RequestBody @Valid ReviewUpdate.Request request
    ) {
        return ReviewUpdate.Response.fromEntity(
                reviewService.updateReview(customerId, reviewId, request)
        );
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void deleteQuestion(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer reviewId
    ) {
        reviewService.deleteReview(customerId, reviewId);
        log.info("질문 삭제 완료");

    }

    /**
     * 좋아요
     */
    @PostMapping("/like")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void likePost(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer reviewId) {

        reviewService.likePost(customerId, reviewId);
    }

}
