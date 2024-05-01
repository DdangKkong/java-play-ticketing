package zerobase18.playticketing.review.dto;

import lombok.*;

import java.util.List;

public class ReviewDetail {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{


        private String playName;

        private String name;

        private String title;

        private String content;

        private Integer likeCount;

        private List<CommentDto> comments;


        public static Response fromEntity(ReviewDto reviewDto) {
            return Response.builder()
                    .playName(reviewDto.getPlayName())
                    .name(reviewDto.getName())
                    .title(reviewDto.getTitle())
                    .likeCount(reviewDto.getLikeCount())
                    .content(reviewDto.getContent())
                    .comments(reviewDto.getCommentDto())
                    .build();
        }


    }

}
