package zerobase18.playticketing.review.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.review.dto.CommentDto;
import zerobase18.playticketing.review.dto.CommentUpdate;
import zerobase18.playticketing.review.dto.CreateComment;
import zerobase18.playticketing.review.entity.Comment;
import zerobase18.playticketing.review.entity.Review;
import zerobase18.playticketing.review.repository.CommentRepository;
import zerobase18.playticketing.review.repository.ReviewRepository;
import zerobase18.playticketing.review.service.CommentService;

import java.util.Objects;
import java.util.Optional;

import static zerobase18.playticketing.global.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CustomerRepository customerRepository;

    private final CommentRepository commentRepository;

    private final ReviewRepository reviewRepository;

    /**
     * 댓글 작성
     */
    @Override
    @Transactional
    public CommentDto createComment(Integer reviewId, Integer commentCustomerId, CreateComment.Request request) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        Customer customer = customerRepository.findById(review.getCustomer().getCustomerId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!review.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            throw new CustomException(CUSTOMER_AUTHORITY_NOT_MATCH);
        }

        Customer commentCustomer = customerRepository.findById(commentCustomerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        Comment save = commentRepository.save(
                Comment.builder()
                        .content(request.getContent())
                        .review(review)
                        .customer(commentCustomer)
                        .build()
        );

        // 코멘트를 Review에 등록
        review.addComment(save);


        return CommentDto.fromEntity(save);
    }

    /**
     * 댓글 수정
     */
    @Override
    @Transactional
    public CommentDto updateComment(Integer customerId, Integer commentId, CommentUpdate.Request request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        if (!comment.getCustomer().equals(customer)) {
            throw new CustomException(CUSTOMER_NOT_MATCH);
        }

        // 댓글 수정 시 입력을 안하면 이전 제목과 내용이 저장.

        String content = Optional.ofNullable(request.getContent()).orElse(comment.getContent());


        comment.setContent(content);



        return CommentDto.fromEntity(comment);
    }


    /**
     * 댓글 삭제
     */
    @Override
    @Transactional
    public void deleteComment(Integer customerId, Integer commentId) {


        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(CUSTOMER_NOT_MATCH));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        if (!Objects.equals(customer.getCustomerId(), comment.getCustomer().getCustomerId())) {
            throw new CustomException(CUSTOMER_NOT_MATCH);
        }

        // 댓글을 삭제하고자 하는 리뷰를 가져온다.
        Review review = comment.getReview();

        if (review != null) {

            commentRepository.delete(comment);

            review.getComments().remove(comment);
            review.updateCommentCount();
            reviewRepository.save(review);

        } else {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
    }
}
