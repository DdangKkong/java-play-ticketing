package zerobase18.playticketing.qna.dto;

import lombok.*;

public class AnswerUpdate {


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


        public static Response fromEntity(AnswerDto answerDto) {

            return Response.builder()
                    .content(answerDto.getContent())
                    .updatedAt(answerDto.getUpdatedAt())
                    .build();
        }



    }

}
