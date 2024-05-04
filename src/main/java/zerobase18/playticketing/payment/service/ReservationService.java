package zerobase18.playticketing.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.payment.dto.ReservationCancelDto;
import zerobase18.playticketing.payment.dto.ReservationDto;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.repository.ReservationRepository;
import zerobase18.playticketing.payment.type.ReserStat;
import zerobase18.playticketing.play.entity.Schedule;
import zerobase18.playticketing.play.entity.ScheduleSeat;
import zerobase18.playticketing.play.repository.ScheduleRepository;
import zerobase18.playticketing.play.repository.ScheduleSeatRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static zerobase18.playticketing.global.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleSeatRepository scheduleSeatRepository;
    private final ScheduleRepository scheduleRepository;

    // 예약 신청
    public ReservationDto applyReservation(ReservationDto reservationDto){
        // dto 에서 entity로 변환
        Reservation reservation = Reservation.fromDto(reservationDto);

        // 예약하려는 좌석 수
        int reservationSeatCnt = reservationDto.getScheduleSeatId().length;

        for (int i = 0; i < reservationSeatCnt; i++) {
            // 예약하려는 좌석 조회
            ScheduleSeat scheduleSeat = scheduleSeatRepository.findById(reservationDto.getScheduleSeatId()[i])
                    .orElseThrow(()-> new CustomException(SCHEDULE_SEAT_NOT_FOUND));

            // 이미 예약된 좌석인 경우
            if (scheduleSeat.getReservation() != null){
                throw new CustomException(ALREADY_RESERVATION_SEAT);
            }

            // 좌석 예약
            scheduleSeat.reservation(reservation);
        }

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
        // 예약 정보 조회
        Reservation reservation = reservationRepository.findById(reserId)
                .orElseThrow(()-> new CustomException(RESERVATION_NOT_FOUND)); // 해당 예약이 없습니다
        // 예약한 스케줄별 좌석과 스케줄 조회
        List<ScheduleSeat> cancelScheduleSeat = scheduleSeatRepository.findByReservation(reservation);
        Schedule schedule = scheduleRepository.findById(cancelScheduleSeat.get(0).getSchedule().getId())
                .orElseThrow(()-> new CustomException(SCHEDULE_NOT_FOUND));

        // 예약 상태가 성공인 경우 (=결제한 예약)
        if (reservation.getReserStat().equals(ReserStat.SUCCESS)) {
            // 결제한 예약 취소
            cancelPayment(reservation,schedule.getScheduleDate());
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

        // 좌석 초기화
        // 예약취소 하려는 좌석 수
        int reservationSeatCnt = cancelScheduleSeat.size();

        for (int i = 0; i < reservationSeatCnt; i++) {
            // 예약이 되어있지 않은 좌석인 경우
            if (cancelScheduleSeat.get(i).getReservation() == null){
                throw new CustomException(NOT_RESERVATION_SEAT);
            }

            // 좌석 예약 취소
            cancelScheduleSeat.get(i).cancelReservation();
        }

        // entity 에서 dto로 변환
        return ReservationDto.fromEntity(reservation);
    }

    // 결제한 예약 취소
    private void cancelPayment(Reservation reservation,LocalDate scheduleDate) {
        // 연극 상영날짜가 지나거나 당일 취소 불가능
        if (scheduleDate.isEqual(LocalDate.now()) || scheduleDate.isBefore(LocalDate.now())){
            throw new CustomException(TOO_OLD_RESERVATION);
        }

        // 환불 금액
        int canceledAmount = reservation.getReserAmount();

        // 기간에 따른 환불 금액 계산
        if (scheduleDate.minusDays(3).isBefore(LocalDate.now())) {
            // 3일 전부터는 50% 환불
            canceledAmount = (int) (canceledAmount * 0.5);

        }else if (scheduleDate.minusDays(7).isBefore(LocalDate.now())) {
            // 일주일 전부터는 70% 환불
            canceledAmount = (int) (canceledAmount * 0.7);
        }

        // 환불 금액 저장
        reservation.canceledAmount(canceledAmount);
    }


}
