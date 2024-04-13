package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashReceipts {

    private String receiptKey;                  // 현금영수증의 키 값
    private String orderId;                     // 주문번호
    private String orderName;                   // 구매상품
    private String type;                        // 현금영수증의 종류
    private String issueNumber;                 // 현금영수증 발급 번호
    private String receiptUrl;                  // 발행된 현금영수증을 확인할 수 있는 주소
    private String businessNumber;              // 현금영수증을 발급한 사업자등록번호
    private String transactionType;             // 현금영수증 발급 종류
    private int amount;                         // 현금영수증 처리된 금액
    private int taxFreeAmount;                  // 면세 처리된 금액
    private String issueStatus;                 // 현금영수증 발급 상태
    private Failure failure;                    // 결제 실패 객체
    private Object customerIdentityNumber;      // 현금영수증 발급에 필요한 소비자 인증수단
    private String requestedAt;                 // 현금영수증 발급 혹은 취소를 요청한 날짜와 시간 정보
}
