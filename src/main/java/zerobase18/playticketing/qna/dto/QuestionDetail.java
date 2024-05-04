package zerobase18.playticketing.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.qna.type.QuestionState;

public class QuestionDetail {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        private Integer customerId;

        private Integer QuestionId;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String name;

        private String title;

        private String content;

        private QuestionState questionState;

        private String answer;

        private String createdAt;





    }

}
