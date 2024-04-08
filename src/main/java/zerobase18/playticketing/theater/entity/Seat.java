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

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

}
