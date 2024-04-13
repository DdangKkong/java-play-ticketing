package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {

    private String url;     // 고객에게 제공할 수 있는 결제수단별 영수증
}
