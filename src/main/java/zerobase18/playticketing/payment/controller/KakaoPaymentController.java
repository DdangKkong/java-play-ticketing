package zerobase18.playticketing.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.payment.dto.PaymentDto;
import zerobase18.playticketing.payment.dto.kakao.*;
import zerobase18.playticketing.payment.service.PaymentService;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/kakao")
public class KakaoPaymentController {

    private final PaymentService paymentService;

    // 카카오 페이 결제 준비
    @PostMapping("/ready")
    public KakaoReadyResponseDto kakaoPaymentReady(@RequestBody KakaoReadyRequestDto kakaoReadyRequestDto){
        log.info("[Controller] kakaoPaymentReady!");
        return paymentService.kakaoPaymentReady(kakaoReadyRequestDto);
    }

    // 카카오 페이 결제 승인
    @GetMapping("/success")
    public PaymentDto kakaoPaymentApprove(@RequestParam int reserId, @RequestParam String pg_token){
        log.info("[Controller] kakaoPaymentApprove!");
        return paymentService.kakaoPaymentApprove(reserId,pg_token);
    }

    // 카카오 페이 결제 취소
    @GetMapping("/cancel")
    public PaymentDto kakaoPaymentCancel(@RequestBody KakaoCancelRequestDto kakaoCancelRequestDto){
        log.info("[Controller] kakaoPaymentCancel!");
        return paymentService.kakaoPaymentCancel(kakaoCancelRequestDto);
    }

    // 카카오 페이 결제 조회
    @GetMapping("/order")
    public KakaoOrderResponseDto kakaoPaymentOrder(@RequestBody KakaoOrderRequestDto kakaoOrderRequestDto){
        log.info("[Controller] kakaoPaymentOrder!");
        return paymentService.kakaoPaymentOrder(kakaoOrderRequestDto);
    }

}
