package zerobase18.playticketing.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.payment.dto.ReservationDto;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.repository.ReservationRepository;
import zerobase18.playticketing.payment.type.ReserStat;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    // 예약 신청
    public ReservationDto applyReservation(ReservationDto reservationDto){
        // dto 에서 entity로 변환
        Reservation reservation = Reservation.fromDto(reservationDto);

        // 예약 신청 설정
        reservation.applyReser();

        // 예약 정보 저장
        // entity 에서 dto로 변환
        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }

    // 예약 취소 (결제한 예약)
    @Transactional
    public ReservationDto cancelPaymentReservation(int reserId){
        log.info("cancelPaymentReservation!");
        // dto 에서 entity로 변환
        Reservation reservation = reservationRepository.findById(reserId)
                .orElseThrow(()-> new RuntimeException()); // 해당 예약이 없습니다

        // 예약 상태가 성공이어야 결제한 예약
        paymentCancelPossible(reservation);

        // 연극 상영날짜가 지나거나 당일 취소 불가능
        LocalDate schedule_date = LocalDate.parse("2024-04-25"); // 임시 코드
        if (schedule_date.isEqual(LocalDate.now()) || schedule_date.isBefore(LocalDate.now())){
            throw new RuntimeException();
        }

        // 환불 금액
        int canceledAmount = reservation.getReserAmount();

        // 기간에 따른 환불 금액 계산
        if (schedule_date.minusDays(3).isBefore(LocalDate.now())) {
            // 3일 전부터는 50% 환불
            canceledAmount = (int) (canceledAmount * 0.5);

        }else if (schedule_date.minusDays(7).isBefore(LocalDate.now())) {
            // 일주일 전부터는 70% 환불
            canceledAmount = (int) (canceledAmount * 0.7);
        }

        // 환불 금액 저장
        reservation.canceledAmount(canceledAmount);

        // entity 에서 dto로 변환
        return ReservationDto.fromEntity(reservation);
    }

    // 예약 취소 요청 (결제하지 않은 예약)
    public ReservationDto cancelApplyReservation(int reserId){
        // dto 에서 entity로 변환
        Reservation reservation = reservationRepository.findById(reserId)
                .orElseThrow(()-> new RuntimeException()); // 해당 예약이 없습니다

        // 예약 상태가 신청이어야 결제하지 않은 예약
        applyCancelPossible(reservation);

        // 예약 신청 정보 삭제
        reservationRepository.delete(reservation);

        // entity 에서 dto로 변환
        return ReservationDto.fromEntity(reservation);
    }

    // 예약 상태가 성공이어야 결제한 예약
    private void paymentCancelPossible(Reservation reservation) {
        if (reservation.getReserStat() != ReserStat.SUCCESS){
            throw new RuntimeException();
        }
    }

    // 예약 상태가 신청이어야 결제하지 않은 예약
    private void applyCancelPossible(Reservation reservation) {
        if (reservation.getReserStat() != ReserStat.APPLY){
            throw new RuntimeException();
        }
    }


}
