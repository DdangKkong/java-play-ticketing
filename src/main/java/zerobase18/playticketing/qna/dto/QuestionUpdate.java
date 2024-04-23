package zerobase18.playticketing.qna.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class QuestionUpdate {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        private String title;

        private String content;


    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private String title;

        private String content;

        private String updatedAt;


        public static Response fromEntity(QuestionDto questionDto) {

            return Response.builder()
                    .title(questionDto.getTitle())
                    .content(questionDto.getContent())
                    .updatedAt(questionDto.getUpdatedAt())
                    .build();
        }



    }

}
