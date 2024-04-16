package zerobase18.playticketing.payment.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Failure {

    private String code;        // 오류 타입을 보여주는 에러 코드
    private String message;     // 에러 메시지
}
