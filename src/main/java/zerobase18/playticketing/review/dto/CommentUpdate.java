package zerobase18.playticketing.review.dto;

import lombok.*;

public class CommentUpdate {


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {


        private String content;



    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {


        private String content;

        private String updatedAt;


        public static Response fromEntity(CommentDto commentDto) {

            return Response.builder()
                    .content(commentDto.getContent())
                    .updatedAt(commentDto.getUpdatedAt())
                    .build();
        }



    }

}
