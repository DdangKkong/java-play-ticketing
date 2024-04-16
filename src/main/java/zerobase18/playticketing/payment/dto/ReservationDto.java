package zerobase18.playticketing.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase18.playticketing.payment.entity.Reservation;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private int reserId;            // 예약 고유번호
    private int customerId;         // 고객 고유번호
    private String reserStat;       // 예약상태
    private int reserAmount;        // 예약금액

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .reserId(reservation.getReserId())
                .customerId(reservation.getCustomerId())
                .reserStat(reservation.getReserStat())
                .reserAmount(reservation.getReserAmount())
                .build();
    }

}
