package zerobase18.playticketing.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.payment.dto.toss.TossApproveRequestDto;
import zerobase18.playticketing.payment.dto.toss.TossCancelRequestDto;
import zerobase18.playticketing.payment.service.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class TossPaymentController {

    private final PaymentService paymentService;

    // 토스 페이먼츠 결제 요청
    @GetMapping("/toss")
    public String tossPayment(){
        log.info("tossPayment!");
        return "ui/checkout";
    }

    // 토스 페이먼츠 결제 요청 성공
    @GetMapping("/toss/success")
    public String tossPaymentSuccess(@RequestParam String paymentType, @RequestParam String orderId,
                                      @RequestParam String paymentKey, @RequestParam int amount){
        log.info("tossPaymentSuccess!");
        log.info("toss orderId : {}",orderId);
        log.info("toss paymentKey : {}",paymentKey);
        log.info("toss amount : {}",amount);
        log.info("toss paymentType : {}",paymentType);
        return "ui/success";
    }

    // 토스 페이먼츠 결제 승인
    @PostMapping("/toss/confirm")
    public ResponseEntity tossPaymentApprove(@RequestBody TossApproveRequestDto tossApproveRequestDto){
        log.info("[Controller] tossPaymentApprove!");
        log.info("orderId : {}",tossApproveRequestDto.getOrderId());
        return ResponseEntity.ok().body(paymentService.tossPaymentApprove(tossApproveRequestDto));
    }

    // 토스 페이먼츠 결제 취소
    @GetMapping("/toss/cancel")
    public ResponseEntity tossPaymentCancel(@RequestBody TossCancelRequestDto tossCancelRequestDto){
        log.info("[Controller] tossPaymentCancel!");
        return ResponseEntity.ok().body(paymentService.tossPaymentCancel(tossCancelRequestDto));
    }
}
