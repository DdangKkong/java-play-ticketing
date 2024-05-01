package zerobase18.playticketing.review.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CreateReview {


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
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String title;

        private String playName;

        private String content;

        private Integer rating;

        private String createdAt;

        public static Response from(ReviewDto reviewDto) {

            return Response.builder()
                    .title(reviewDto.getTitle())
                    .playName(reviewDto.getPlayName())
                    .content(reviewDto.getContent())
                    .rating(reviewDto.getRating())
                    .createdAt(reviewDto.getCreatedAt())
                    .build();

        }

    }

}
