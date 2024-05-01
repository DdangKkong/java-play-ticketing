package zerobase18.playticketing.qna.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.global.entity.BaseEntity;
import zerobase18.playticketing.qna.type.QuestionState;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Answer extends BaseEntity {

    // 답변 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    // 내용
    @NotNull
    private String content;



    @ManyToOne
    private Question question;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Admin admin;

    @CreatedDate
    private String createdAt;


}
