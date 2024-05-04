package zerobase18.playticketing.review.dto;

import lombok.*;
import zerobase18.playticketing.review.entity.Comment;
import zerobase18.playticketing.review.entity.Review;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private String title;

    private String name;

    private String playName;

    private String content;

    private Integer rating;

    private Integer commentCount;

    private Integer likeCount;

    private Long viewCount;

    private List<LikeDto> likeDto;

    private List<CommentDto> commentDto;

    private String createdAt;

    private String updatedAt;

    public static ReviewDto fromEntity(Review review) {
        /**
         * 댓글 목록 리스트 형태
         */
        List<CommentDto> comments;
        if (review.getComments() != null) {
            comments = review.getComments().stream()
                    // 최신 순으로 정렬
                    .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                    .map(comment -> CommentDto.builder()
                            .playName(comment.getReview().getReservation().getReserPlayName())
                            .commentCustomer(comment.getCustomer().getName())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build())
                    .collect(Collectors.toList());
        } else {
            comments = Collections.emptyList(); // 댓글 리스트가 널인 경우 빈 리스트로 초기화
        }

        List<LikeDto> likes;

        if (review.getLikes() != null) {
            likes = review.getLikes().stream()
                    .map(like -> LikeDto.builder()
                            .name(review.getCustomer().getName())
                            .build()
                    ).collect(Collectors.toList());
        } else {
            likes = Collections.emptyList();
        }


        return ReviewDto.builder()
                .title(review.getTitle())
                .name(review.getCustomer().getName())
                .playName(review.getReservation().getReserPlayName())
                .content(review.getContent())
                .rating(review.getRating())
                .commentCount(review.getComments() != null ? review.getComments().size() : 0) // 널 체크 후 사이즈 계산
                .viewCount(review.getViewCount())
                .likeCount(review.getLikeCount())
                .commentDto(comments)
                .likeDto(likes)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

}
