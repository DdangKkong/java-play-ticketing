package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAccount {

    private String accountType;                             // 가상계좌 타입
    private String accountNumber;                           // 발급된 계좌번호
    private String bankCode;                                // 가상계좌 은행 숫자 코드
    private String customerName;                            // 가상계좌를 발급한 구매자명
    private String dueDate;                                 // 입금 기한
    private String refundStatus;                            // 환불 처리 상태
    private boolean expired;                                // 가상계좌의 만료 여부
    private String settlementStatus;                        // 정산 상태
    private RefundReceiveAccount refundReceiveAccount;      // 결제위젯 가상계좌 환불 정보 입력 기능으로 받은 고객의 환불 계좌 정보
}
