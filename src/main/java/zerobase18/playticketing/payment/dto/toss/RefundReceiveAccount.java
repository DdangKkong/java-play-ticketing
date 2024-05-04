package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundReceiveAccount {

    private String bank;                // 취소 금액을 환불받을 계좌의 은행 코드
    private String accountNumber;       // 취소 금액을 환불받을 계좌의 계좌번호
    private String holderName;          // 취소 금액을 환불받을 계좌의 예금주

}
