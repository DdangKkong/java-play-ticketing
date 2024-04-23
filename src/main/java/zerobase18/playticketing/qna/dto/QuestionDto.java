package zerobase18.playticketing.qna.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import zerobase18.playticketing.qna.entity.Question;
import zerobase18.playticketing.qna.type.QuestionState;

@Builder
@AllArgsConstructor
@Getter
public class QuestionDto {


    private String name;

    private String title;

    private String content;

    private String answer;

    private QuestionState questionState;

    private String createdAt;

    private String updatedAt;


    public static QuestionDto fromEntity(Question question) {

        return QuestionDto.builder()
                .name(question.getCustomer().getName())
                .title(question.getTitle())
                .content(question.getContent())
                .questionState(question.getQuestionState())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }

}
