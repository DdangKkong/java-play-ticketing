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

    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;
    private int totalAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private Card card ;

}
