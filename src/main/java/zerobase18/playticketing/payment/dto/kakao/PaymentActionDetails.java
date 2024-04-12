package zerobase18.playticketing.payment.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentActionDetails {

    private String aid;                     // Request 고유 번호
    private LocalDateTime approved_at;      // 거래시간
    private int amount;                     // 결제/취소 총액
    private int point_amount;               // 결제/취소 포인트 금액
    private int discount_amount;            // 할인 금액
    private int green_deposit;              // 컵 보증금
    private String payment_action_type;     // 결제 타입
    private String payload;                 // Request로 전달한 값

}
