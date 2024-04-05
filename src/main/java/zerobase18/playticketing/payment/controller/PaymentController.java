package zerobase18.playticketing.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.payment.dto.kakao.KakaoApproveResponseDto;
import zerobase18.playticketing.payment.dto.kakao.KakaoReadyRequestDto;
import zerobase18.playticketing.payment.dto.kakao.KakaoReadyResponseDto;
import zerobase18.playticketing.payment.service.PaymentService;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    // 카카오 페이 결제 준비
    @PostMapping("/ready")
    public KakaoReadyResponseDto kakaoPaymentReady(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto){
        log.info("[Controller] paymentReady!");
        return paymentService.kakaoPaymentReady(kakaoReadyRequestDto);
    }

    // 카카오 페이 결제 승인
    @GetMapping("/success")
    public KakaoApproveResponseDto kakaoPaymentApprove(@RequestParam String pg_token){
        log.info("[Controller] paymentApprove!");
        return paymentService.kakaoPaymentApprove(pg_token);
    }

}
