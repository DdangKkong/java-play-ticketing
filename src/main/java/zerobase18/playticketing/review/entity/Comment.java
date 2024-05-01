package zerobase18.playticketing.review.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.global.entity.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment extends BaseEntity {


    // 댓글 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    // 댓글 내용
    @NotNull
    private String content;


    @ManyToOne
    private Review review;

    @ManyToOne
    private Customer customer;



}
