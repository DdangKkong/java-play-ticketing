package zerobase18.playticketing.qna.dto;

import lombok.*;


public class CreateAnswer {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private Integer customerId;

        private Integer questionId;

        private String content;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        private String name;

        private String content;

        private String createdAt;

        public static Response fromEntity(AnswerDto answerDto) {
            return Response.builder()
                    .name(answerDto.getName())
                    .content(answerDto.getContent())
                    .createdAt(answerDto.getCreatedAt())
                    .build();
        }

    }

}
