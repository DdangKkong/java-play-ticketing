package zerobase18.playticketing.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.payment.dto.ReservationDto;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.repository.ReservationRepository;
import zerobase18.playticketing.payment.type.ReserStat;

import java.time.LocalDate;

import static zerobase18.playticketing.global.type.ErrorCode.*;

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

    // 예약 취소
    @Transactional
    public ReservationDto cancelPaymentReservation(int reserId){
        log.info("cancelPaymentReservation!");
        // dto 에서 entity로 변환
        Reservation reservation = reservationRepository.findById(reserId)
                .orElseThrow(()-> new CustomException(RESERVATION_NOT_FOUND)); // 해당 예약이 없습니다

        // 예약 상태가 성공인 경우 (=결제한 예약)
        if (reservation.getReserStat().equals(ReserStat.SUCCESS)) {
            // 결제한 예약 취소
            cancelPayment(reservation);
        }
        // 예약 상태가 신청인 경우 (=결제하지 않은 예약)
        else if (reservation.getReserStat().equals(ReserStat.APPLY)){
            // 결제하지 않은 예약 삭제
            reservationRepository.delete(reservation);
        }
        else {
            // 이미 결제 취소한 상태입니다
            throw new CustomException(ALREADY_CANCEL_PAYMENT);
        }

        // entity 에서 dto로 변환
        return ReservationDto.fromEntity(reservation);
    }

    // 결제한 예약 취소
    private void cancelPayment(Reservation reservation) {
        // 연극 상영날짜가 지나거나 당일 취소 불가능
        LocalDate schedule_date = LocalDate.parse("2024-04-25"); // 임시 코드
        if (schedule_date.isEqual(LocalDate.now()) || schedule_date.isBefore(LocalDate.now())){
            throw new CustomException(TOO_OLD_RESERVATION);
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
    }


}
