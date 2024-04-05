package zerobase18.playticketing.payment.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoReadyRequestDto {


    private String cid;                 // 가맹점 코드
    private String partner_order_id;    // 가맹점 주문번호
    private String partner_user_id;     // 가맹점 회원 id
    private String item_name;           // 상품명
    private int quantity;               // 상품 수량
    private int total_amount;           // 상품 총액
    private int tax_free_amount;        // 상품 비과세 금액
    private String approval_url;        // 결제 성공시 리다이렉트 url
    private String cancel_url;          // 결제 취소시 리다이렉트 url
    private String fail_url;            // 결제 실패시 리다이렉트 url



}
