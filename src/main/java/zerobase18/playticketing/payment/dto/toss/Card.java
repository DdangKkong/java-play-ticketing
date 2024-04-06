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

    private int amount;
    private String issuerCode;
    private String acquirerCode;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
}
