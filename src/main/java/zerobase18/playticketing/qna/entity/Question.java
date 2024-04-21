package zerobase18.playticketing.qna.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.global.entity.BaseEntity;
import zerobase18.playticketing.qna.type.QuestionState;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Question extends BaseEntity {

    // 질문 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    // 제목
    @NotNull
    private String title;

    // 내용
    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionState questionState;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Answer answer;




}
