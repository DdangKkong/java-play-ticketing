package zerobase18.playticketing.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase18.playticketing.payment.dto.kakao.KakaoApproveResponseDto;
import zerobase18.playticketing.payment.dto.kakao.KakaoCancelResponseDto;
import zerobase18.playticketing.payment.dto.toss.TossApproveResponseDto;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;              // 결제정보 고유번호

    @Column(name = "reser_id")
    private int reserId;                // 예약 고유번호

    @Column(name = "tid_payment_key")
    private String tidPaymentKey;       // 결제 고유번호

    @Column(name = "order_name")
    private String orderName;           // 구매 상품

    @Column(name = "method")
    private String method;              // 결제 수단

    @Column(name = "total_amount")
    private int totalAmount;            // 결제 금액

    @Column(name = "request_at")
    private String requestAt;           // 결제 일시

    @Column(name = "canceled_at")
    private String canceledAt;          // 결제 취소 일시

    @Column(name = "cancel_reason")
    private String cancelReason;        // 결제 취소 사유

    // 예약 고유번호 설정
    public void addReserId(int reserId){
        this.reserId = reserId;
    }

    // 취소 일시, 취소 사유 설정
    public void cancel(String canceledAt,String cancelReason){
        this.canceledAt = canceledAt;
        this.cancelReason = cancelReason;

    }

    public static Payment fromTossDto(TossApproveResponseDto tossApproveResponseDto){
        String canceledAt = null;
        String cancelReason = null;
        // 토스 결제 취소 응답일 경우 (=결제 취소 이력이 있을경우)
        if (tossApproveResponseDto.getCancels() != null) {
            canceledAt = tossApproveResponseDto.getCancels()[0].getCanceledAt();
            cancelReason = tossApproveResponseDto.getCancels()[0].getCancelReason();
        }

        return Payment.builder()
                .tidPaymentKey(tossApproveResponseDto.getPaymentKey())
                .orderName(tossApproveResponseDto.getOrderName())
                .method(tossApproveResponseDto.getMethod())
                .totalAmount(tossApproveResponseDto.getTotalAmount())
                .requestAt(tossApproveResponseDto.getApprovedAt())
                .canceledAt(canceledAt)
                .cancelReason(cancelReason)
                .build();
    }

    public static Payment fromKakaoApproveDto(KakaoApproveResponseDto kakaoApproveResponseDto){
        return Payment.builder()
                .tidPaymentKey(kakaoApproveResponseDto.getTid())
                .orderName(kakaoApproveResponseDto.getItem_name())
                .method(kakaoApproveResponseDto.getPayment_method_type())
                .totalAmount(kakaoApproveResponseDto.getAmount().getTotal())
                .requestAt(kakaoApproveResponseDto.getApproved_at().toString())
                .build();
    }

    public static Payment fromKakaoCancelDto(KakaoCancelResponseDto kakaoCancelResponseDto){
        return Payment.builder()
                .canceledAt(kakaoCancelResponseDto.getCanceled_at().toString())
                .cancelReason(kakaoCancelResponseDto.getPayload())
                .build();
    }

}
