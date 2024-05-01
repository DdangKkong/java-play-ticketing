package zerobase18.playticketing.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.qna.dto.QuestionDto;

public class ReviewUpdate {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        private String title;

        private String content;

        private Integer rating;


    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private String title;

        private String content;

        private Integer rating;

        private String updatedAt;


        public static Response fromEntity(ReviewDto reviewDto) {

            return Response.builder()
                    .title(reviewDto.getTitle())
                    .content(reviewDto.getContent())
                    .rating(reviewDto.getRating())
                    .updatedAt(reviewDto.getUpdatedAt())
                    .build();
        }



    }

}
