package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobilePhone {

    private Object customerMobilePhone;     // 결제에 사용한 휴대폰 번호
    private String settlementStatus;        // 정산 상태
    private String receiptUrl;              // 휴대폰 결제 내역 영수증을 확인할 수 있는 주소


}
