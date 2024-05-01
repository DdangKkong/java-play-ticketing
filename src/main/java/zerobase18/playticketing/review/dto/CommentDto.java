package zerobase18.playticketing.review.dto;

import lombok.*;
import zerobase18.playticketing.review.entity.Comment;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private String playName;

    private String content;

    private String commentCustomer;

    private String createdAt;

    private String updatedAt;

    public static CommentDto fromEntity(Comment comment) {

        return CommentDto.builder()
                .playName(comment.getReview().getReservation().getReserPlayName())
                .content(comment.getContent())
                .commentCustomer(comment.getCustomer().getName())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }



}
