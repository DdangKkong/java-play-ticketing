package zerobase18.playticketing.review.service;

import zerobase18.playticketing.review.dto.*;

public interface CommentService {

    /**
     * 댓글 작성
     */
    CommentDto createComment(Integer reviewId, Integer commentCustomerId, CreateComment.Request request);


    /**
     * 댓글 수정
     */
    CommentDto updateComment(Integer customerId, Integer commentId, CommentUpdate.Request request);

    /**
     * 댓글 삭제
     */
    void deleteComment(Integer customerId, Integer commentId);


}
