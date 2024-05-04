package zerobase18.playticketing.review.dto;

import lombok.*;

public class CreateComment {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class Request{

        private String content;

    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Builder
    public static class Response {

        private String playName;

        private String commentCustomer;

        private String content;


        private String createdAt;

        public static Response fromEntity(CommentDto commentDto) {

            return Response.builder()
                    .playName(commentDto.getPlayName())
                    .commentCustomer(commentDto.getCommentCustomer())
                    .content(commentDto.getContent())
                    .createdAt(commentDto.getCreatedAt())
                    .build();
        }


    }
}
