package zerobase18.playticketing.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.payment.dto.ReservationDto;
import zerobase18.playticketing.payment.service.ReservationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 신청
    @PostMapping("/apply")
    public ReservationDto applyReservation(@RequestBody ReservationDto reservationDto){
        log.info("applyReservation!");
        return reservationService.applyReservation(reservationDto);
    }

}
