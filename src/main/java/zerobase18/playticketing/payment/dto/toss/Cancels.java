package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cancels {

    private int cancelAmount;               // 결제를 취소한 금액
    private String cancelReason;            // 결제를 취소한 이유
    private int taxFreeAmount;              // 취소된 금액 중 면세 금액
    private int taxExemptionAmount;         // 취소된 금액 중 과세 제외 금액(컵 보증금 등)
    private int refundableAmount;           // 결제 취소 후 환불 가능한 잔액
    private int easyPayDiscountAmount;      // 간편결제 서비스의 포인트, 쿠폰, 즉시할인과 같은 적립식 결제수단에서 취소된 금액
    private String canceledAt;              // 결제 취소가 일어난 날짜와 시간 정보
    private String transactionKey;          // 취소 건의 키 값
    private String receiptKey;              // 취소 건의 현금영수증 키 값
    private String cancelStatus;            // 취소 상태
    private String cancelRequestId;         // 취소 요청 ID

}
