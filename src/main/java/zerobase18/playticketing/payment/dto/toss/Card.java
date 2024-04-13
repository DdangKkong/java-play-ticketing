package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private int amount;                     // 카드사에 결제 요청한 금액
    private String issuerCode;              // 카드 발급사 숫자 코드
    private String acquirerCode;            // 카드 매입사 숫자 코드
    private String number ;                 // 카드번호
    private int installmentPlanMonths ;     // 할부 개월 수
    private String approveNo ;              // 카드사 승인 번호
    private boolean useCardPoint ;          // 카드사 포인트 사용 여부
    private String cardType;                // 카드 종류
    private String ownerType;               // 카드의 소유자 타입
    private String acquireStatus;           // 카드 결제의 매입 상태
    private boolean isInterestFree ;        // 무이자 할부의 적용 여부
    private String interestPayer ;          // 할부가 적용된 결제에서 할부 수수료를 부담하는 주체
}
