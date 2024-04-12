package zerobase18.playticketing.theater.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int id;

    // 좌석 열 (알파벳)
    @Column(name = "seat_row")
    private char seatRow;

    // 좌석 번호
    @Column(name = "seat_num")
    private int seatNum;

    // 좌석 타입
    @Column(name = "seat_type")
    private String  seatType;

    // 좌석 가격
    @Column(name = "seat_price")
    private int seatPrice;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

}
