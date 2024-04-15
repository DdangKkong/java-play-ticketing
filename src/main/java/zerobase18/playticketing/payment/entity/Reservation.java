package zerobase18.playticketing.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase18.playticketing.payment.dto.ReservationDto;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reser_id")
    private int reserId;            // 예약 고유번호

    @Column(name = "customer_id")
    private int customerId;         // 고객 고유번호

    @Column(name = "reser_stat")
    private String reserStat;       // 예약상태

    @Column(name = "reser_amount")
    private int reserAmount;        // 예약금액

    // 예약 신청 설정
    public void applyReser(){
        this.reserStat = "APPLY";
    }

    // 예약 완료 설정
    public void successReser(){
        this.reserStat = "SUCCESS";
    }

    // 예약 취소 설정
    public void canceledReser(){
        this.reserStat = "CANCEL";
    }

    public static Reservation fromDto(ReservationDto reservationDto){
        return Reservation.builder()
                .reserId(reservationDto.getReserId())
                .customerId(reservationDto.getCustomerId())
                .reserStat(reservationDto.getReserStat())
                .reserAmount(reservationDto.getReserAmount())
                .build();
    }


}
