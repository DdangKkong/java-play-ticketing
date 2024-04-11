package zerobase18.playticketing.payment.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoCancelResponseDto {

    private String aid;
    private String tid;
    private String cid;
    private String status;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private Amount amount;
    private ApprovedCancelAmount approved_cancel_amount;
    private CanceledAmount canceled_amount;
    private CancelAvailableAmount cancel_available_amount;
    private String item_name;
    private String item_code;
    private int quantity;
    private LocalDateTime created_at;
    private LocalDateTime approved_at;
    private LocalDateTime canceled_at;
    private String payload;


}
