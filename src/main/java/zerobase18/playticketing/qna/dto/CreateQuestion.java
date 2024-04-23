package zerobase18.playticketing.qna.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.qna.dto.QuestionDto;
import zerobase18.playticketing.qna.type.QuestionState;


public class CreateQuestion {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        private Integer customerId;

        private String title;

        private String content;


    }
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private String name;

        private String title;

        private String content;

        private QuestionState questionState;

        public static Response fromEntity(QuestionDto questionDto) {

            return Response.builder()
                    .name(questionDto.getName())
                    .title(questionDto.getTitle())
                    .content(questionDto.getContent())
                    .questionState(questionDto.getQuestionState())
                    .build();
        }

    }
}
