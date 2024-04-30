package zerobase18.playticketing.review.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.global.entity.BaseEntity;
import zerobase18.playticketing.payment.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review extends BaseEntity {


    // 리뷰 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    // 리뷰 제목
    @NotNull
    private String title;

    // 리뷰 내용
    @NotNull
    private String content;

    // 리뷰 평점
    @NotNull
    @Range(min = 1, max = 5, message = "평점은 1~5점 까지만 등록이 가능합니다.")
    private Integer rating;

    private Long viewCount = 0L;


    // 댓글 갯수
    private int commentCount;

    private int likeCount;


    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Reservation reservation;


    // 댓글은 여러 개 이므로 List로 반환
    @OneToMany
    @Nullable
    private List<Comment> comment;

    @OneToMany
    @Nullable
    private List<Like> likes;

    // 댓글 생성 시 리뷰 엔티티에 댓글 등록
    public void addComment(Comment newComment) {
        if (comment == null) {
            comment = new ArrayList<>();
        }
        comment.add(newComment);
        updateCommentCount();
    }

    // 댓글 갯수 카운팅
    public int updateCommentCount() {
        if (comment != null) {
            return commentCount = comment.size();
        } else {
            return commentCount = 0;
        }
    }

    public void addLike(Like newComment) {
        if (likes == null) {
            likes = new ArrayList<>();
        }
        likes.add(newComment);
        updateLikeCount();
    }

    // 댓글 갯수 카운팅
    public int updateLikeCount() {
        if (likes != null) {
            return likeCount = likes.size();
        } else {
            return likeCount = 0;
        }
    }


}
