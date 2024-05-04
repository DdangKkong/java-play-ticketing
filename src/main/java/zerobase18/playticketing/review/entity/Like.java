package zerobase18.playticketing.review.entity;


import jakarta.persistence.*;
import lombok.*;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.global.entity.BaseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_like")
public class Like extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Review review;

    @Column(nullable = false)
    private Boolean isLike;

}
