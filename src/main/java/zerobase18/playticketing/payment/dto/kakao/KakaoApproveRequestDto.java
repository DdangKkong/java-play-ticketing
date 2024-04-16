package zerobase18.playticketing.payment.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoApproveRequestDto {

    private String cid;                     // 가맹점 코드
    private String tid;                     // 결제 고유번호
    private String partner_order_id;        // 가맹점 주문번호
    private String partner_user_id;         // 가맹점 회원 id
    private String pg_token;                // 결제승인 요청을 인증하는 토큰

}
