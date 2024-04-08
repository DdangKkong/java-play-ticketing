package zerobase18.playticketing.theater.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_type_id")
    private int id;

    // 좌석 타입
    @Column(name = "seat_type")
    private String seatType;

    // 좌석 가격
    @Column(name = "seat_price")
    private int seatPrice;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

}
