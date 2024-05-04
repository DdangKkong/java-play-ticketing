package zerobase18.playticketing.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.type.ReserStat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private int reserId;                // 예약 고유번호
    private int customerId;             // 고객 고유번호
    private String reserPlayName;       // 예약 연극명
    private ReserStat reserStat;        // 예약상태
    private int reserAmount;            // 예약금액

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reserAt;      // 예약일시

    private int cancelAmount;           // 환불 가능한 금액

    // parameter
    private int[] scheduleSeatId;       // 스케줄별 좌석 고유번호

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .reserId(reservation.getReserId())
                .customerId(reservation.getCustomerId().getCustomerId())
                .reserPlayName(reservation.getReserPlayName())
                .reserStat(reservation.getReserStat())
                .reserAmount(reservation.getReserAmount())
                .cancelAmount(reservation.getCancelAmount())
                .build();
    }

}
