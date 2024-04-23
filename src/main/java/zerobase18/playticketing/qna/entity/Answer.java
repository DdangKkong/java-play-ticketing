package zerobase18.playticketing.qna.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.qna.type.QuestionState;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Answer {

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
