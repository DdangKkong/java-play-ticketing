package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashReceipt {

    private String type;                // 현금영수증의 종류
    private String receiptKey;          // 현금영수증의 키 값
    private String issueNumber;         // 현금영수증 발급 번호
    private String receiptUrl;          // 발행된 현금영수증을 확인할 수 있는 주소
    private String amount;              // 현금영수증 처리된 금액
    private String taxFreeAmount;       // 면세 처리된 금액
}
