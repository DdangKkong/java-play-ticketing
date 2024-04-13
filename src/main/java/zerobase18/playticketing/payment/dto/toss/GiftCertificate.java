package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

    private String approveNo;               // 결제 승인번호
    private String settlementStatus;        // 정산 상태

}
