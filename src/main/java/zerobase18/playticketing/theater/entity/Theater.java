package zerobase18.playticketing.theater.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private int id;

    // 극장명
    @Column(name = "theater_name")
    private String theaterName;

    // 극장 주소
    @Column(name = "theater_adress")
    private String theaterAdress;

    // 총 좌석 수
    @Column(name = "seat_total_num")
    private int seatTotalCount;

    // 좌석의 열 수 (가로)
    @Column(name = "seat_row_num")
    private int seatRowCount;

    // 생성 일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 수정 일시
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 삭제 일시
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;



}
