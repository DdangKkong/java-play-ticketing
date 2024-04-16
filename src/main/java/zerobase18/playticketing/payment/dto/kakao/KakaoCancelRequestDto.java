package zerobase18.playticketing.payment.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoCancelRequestDto {

    private String cid;                     // 가맹점 코드
    private String tid;                     // 결제 고유번호
    private int cancel_amount;              // 취소 금액
    private int cancel_tax_free_amount;     // 취소 비과세 금액

}
