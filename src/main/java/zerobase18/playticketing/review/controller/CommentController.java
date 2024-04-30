package zerobase18.playticketing.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.review.dto.CommentUpdate;
import zerobase18.playticketing.review.dto.CreateComment;
import zerobase18.playticketing.review.service.CommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    /**
     * 댓글 작성
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public CreateComment.Response createComment(
            @RequestParam @Valid Integer reviewId,
            @RequestParam @Valid Integer commentCustomerId,
            @RequestBody CreateComment.Request request
    ) {

        return CreateComment.Response.fromEntity(
                commentService.createComment(reviewId, commentCustomerId, request)
        );
    }

    /**
     * 댓글 수정
     */

    @PutMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public CommentUpdate.Response updateComment(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer commentId,
            @RequestBody CommentUpdate.Request request
    ) {
        return CommentUpdate.Response.fromEntity(
                commentService.updateComment(customerId, commentId, request)
        );
    }


    /**
     * 댓글 삭제
     */
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void deleteQuestion(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer commentId
    ) {
        commentService.deleteComment(customerId, commentId);
        log.info("댓글 삭제 완료");

    }

}
