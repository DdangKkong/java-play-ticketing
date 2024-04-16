package zerobase18.playticketing.payment.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectedCardInfo {

    private String card_bin;                    // 카드 BIN
    private int install_month;                  // 할부 개월 수
    private String installment_type;            // 할부 유형(24.02.01일부터 제공)
    private String card_corp_name;              // 카드사 정보
    private String interest_free_install;       // 무이자할부 여부(Y/N)


}
