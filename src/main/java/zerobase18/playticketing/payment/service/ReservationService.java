package zerobase18.playticketing.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.payment.dto.ReservationDto;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.repository.ReservationRepository;

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
        //reservation.applyReser();

        // 예약 정보 저장
        Reservation savedReservation = reservationRepository.save(reservation);

        // entity 에서 dto로 변환
        return ReservationDto.fromEntity(savedReservation);
    }

}
