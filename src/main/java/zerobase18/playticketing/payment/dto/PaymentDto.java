package zerobase18.playticketing.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase18.playticketing.payment.entity.Payment;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private int paymentId;              // 결제정보 고유번호
    private int reserId;                // 예약 고유번호
    private String tidPaymentKey;       // 결제 고유번호
    private String orderName;           // 구매상품
    private String method;              // 결제수단
    private int totalAmount;            // 결제금액
    private String requestAt;           // 결제일시
    private String canceledAt;          // 결제취소일시
    private String cancelReason;        // 결제취소사유
    private int refundableAmount;       // 결제 취소 후 환불 가능한 잔액

    public static PaymentDto fromEntity(Payment payment){
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .reserId(payment.getReserId().getReserId())
                .tidPaymentKey(payment.getTidPaymentKey())
                .orderName(payment.getOrderName())
                .method(payment.getMethod())
                .totalAmount(payment.getTotalAmount())
                .requestAt(payment.getRequestAt())
                .canceledAt(payment.getCanceledAt())
                .cancelReason(payment.getCancelReason())
                .refundableAmount(payment.getRefundableAmount())
                .build();
    }

}
