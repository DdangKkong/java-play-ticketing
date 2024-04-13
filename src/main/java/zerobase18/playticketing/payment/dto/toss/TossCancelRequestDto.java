package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossCancelRequestDto {

    private String cancelReason;                            // 결제를 취소하는 이유
    private int cancelAmount;                               // 취소할 금액
    private RefundReceiveAccount refundReceiveAccount;      // 결제 취소 후 금액이 환불될 계좌의 정보
    private int taxFreeAmount;                              // 취소할 금액 중 면세 금액
    private String currency;                                // 취소 통화

}
