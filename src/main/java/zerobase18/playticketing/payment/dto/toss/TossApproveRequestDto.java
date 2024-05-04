package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossApproveRequestDto {

    private String paymentKey;      // 결제의 키 값
    private int reserId;            // 예약 고유번호
    private String orderId;         // 주문번호
    private int amount;             // 결제할 금액

}
