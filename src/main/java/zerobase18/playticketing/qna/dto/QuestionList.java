package zerobase18.playticketing.qna.dto;


import lombok.*;
import org.springframework.data.domain.Page;
import zerobase18.playticketing.qna.entity.Question;
import zerobase18.playticketing.qna.type.QuestionState;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionList {

    private String title;

    private String name;

    private QuestionState questionState;

    private String createdAt;

    public static Page<QuestionList> questionLists(Page<Question> questions) {
        return questions.map(question -> QuestionList.builder()
                .title(question.getTitle())
                .name(question.getCustomer().getName())
                .questionState(question.getQuestionState())
                .createdAt(question.getCreatedAt())
                .build());
    }

}
