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
public class KakaoReadyResponseDto {

    private String tid;                             // 결제 고유 번호
    // 카카오톡 결제 페이지 리다이렉트 url
    private String next_redirect_app_url;           // 요청한 클라이언트가 모바일 앱일경우
    private String next_redirect_mobile_url;        // 요청한 클라이언트가 모바일 웹일경우
    private String next_redirect_pc_url;            // 요청한 클라이언트가 PC 웹일 경우

    private String android_app_scheme;              // 카카오페이 결제 화면으로 이동하는 안드로이드 앱 스킴
    private String ios_app_scheme;                  // 카카오페이 결제 화면으로 이동하는 IOS DOQ TMZLA
    private LocalDateTime created_at;               // 결제 준비 요청 시간
}
