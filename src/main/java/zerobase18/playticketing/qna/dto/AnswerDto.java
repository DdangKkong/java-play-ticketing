package zerobase18.playticketing.qna.dto;


import lombok.*;
import zerobase18.playticketing.qna.entity.Answer;
import zerobase18.playticketing.qna.type.QuestionState;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {

    private String name;

    private String content;


    private String createdAt;

    public static AnswerDto fromEntity(Answer answer) {

        return AnswerDto.builder()
                .name(answer.getAdmin().getName())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .build();

    }


}
