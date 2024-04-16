package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossApproveResponseDto {

    private String version;                         // Payment 객체의 응답 버전
    private String paymentKey;                      // 결제의 키 값
    private String type;                            // 결제 타입 정보
    private String orderId;                         // 주문번호
    private String orderName;                       // 구매상품
    private String mId;                             // 상점아이디(MID)
    private String currency;                        // 결제할 때 사용한 통화
    private String method;                          // 결제수단
    private int totalAmount;                        // 총 결제 금액
    private int balanceAmount;                      // 취소할 수 있는 금액(잔고)
    private String status;                          // 결제 처리 상태
    private String requestedAt;                     // 결제가 일어난 날짜와 시간 정보
    private String approvedAt;                      // 결제 승인이 일어난 날짜와 시간 정보
    private boolean useEscrow;                      // 에스크로 사용 여부
    private String lastTransactionKey;              // 마지막 거래의 키 값
    private int suppliedAmount;                     // 공급가액
    private int vat;                                // 부가세
    private boolean cultureExpense;                 // 문화비(도서, 공연 티켓, 박물관·미술관 입장권 등) 지출 여부
    private int taxFreeAmount;                      // 결제 금액 중 면세 금액
    private int taxExemptionAmount;                 // 과세를 제외한 결제 금액(컵 보증금 등)
    private Cancels[] cancels;                      // 결제 취소 이력
    private boolean isPartialCancelable;            // 부분 취소 가능 여부
    private Card card;                              // 카드로 결제하면 제공되는 카드 관련 정보
    private VirtualAccount virtualAccount;          // 가상계좌로 결제하면 제공되는 가상계좌 관련 정보
    private String secret;                          // 가상계좌 웹훅이 정상적인 요청인지 검증하는 값
    private MobilePhone mobilePhone;                // 휴대폰으로 결제하면 제공되는 휴대폰 결제 관련 정보
    private GiftCertificate giftCertificate;        // 상품권으로 결제하면 제공되는 상품권 결제 관련 정보
    private Transfer transfer;                      // 계좌이체로 결제했을 때 이체 정보가 담기는 객체
    private Receipt receipt;                        // 발행된 영수증 정보
    private Checkout checkout;                      // 결제창 정보
    private EasyPay easyPay;                        // 간편결제 정보
    private String country;                         // 결제한 국가
    private Failure failure;                        // 결제 승인에 실패하면 응답으로 받는 에러 객체
    private CashReceipt cashReceipt;                // 현금영수증 정보
    private CashReceipt[] cashReceipts;             // 현금영수증 발행 및 취소 이력이 담기는 배열
    private Discount discount;                      // 카드사의 즉시 할인 프로모션 정보

}
