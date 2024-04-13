package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EasyPay {

    private String provider;        // 선택한 간편결제사 코드
    private int amount;             // 간편결제 서비스에 등록된 계좌 혹은 현금성 포인트로 결제한 금액
    private int discountAmount;     // 간편결제 서비스의 적립 포인트나 쿠폰 등으로 즉시 할인된 금액

}
