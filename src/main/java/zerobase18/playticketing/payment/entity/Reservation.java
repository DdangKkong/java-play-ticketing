package zerobase18.playticketing.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.payment.dto.ReservationDto;
import zerobase18.playticketing.payment.type.ReserStat;

import javax.naming.Name;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reser_id")
    private int reserId;                // 예약 고유번호

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customerId;        // 고객 고유번호

    @Column(name = "reser_play_name")
    private String reserPlayName;       // 예약 연극명

    @Column(name = "reser_stat")
    @Enumerated(EnumType.STRING)
    private ReserStat reserStat;        // 예약상태

    @Column(name = "reser_amount")
    private int reserAmount;            // 예약금액

    @Column(name = "reser_at")
    private LocalDateTime reserAt;      // 예약일시

    @Column(name = "cancel_amount")
    private int cancelAmount;           // 환불 가능한 금액

    // 예약 신청 설정
    public void applyReser(){
        this.reserStat = ReserStat.APPLY;
    }

    // 예약 완료 설정
    public void successReser(){
        this.reserStat = ReserStat.SUCCESS;
    }

    // 예약 취소 설정
    public void canceledReser(){
        this.reserStat = ReserStat.CANCEL;
    }

    // 환불 금액 설정
    public void canceledAmount(int cancelAmount){
        this.cancelAmount = cancelAmount;
    }

    public static Reservation fromDto(ReservationDto reservationDto){
        return Reservation.builder()
                .reserId(reservationDto.getReserId())
                .customerId(Customer.builder()
                        .customerId(reservationDto.getCustomerId())
                        .build())
                .reserStat(reservationDto.getReserStat())
                .reserPlayName(reservationDto.getReserPlayName())
                .reserAmount(reservationDto.getReserAmount())
                .reserAt(reservationDto.getReserAt())
                .cancelAmount(reservationDto.getCancelAmount())
                .build();
    }


}
