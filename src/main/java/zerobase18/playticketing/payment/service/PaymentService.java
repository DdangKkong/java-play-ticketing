package zerobase18.playticketing.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zerobase18.playticketing.payment.dto.kakao.KakaoApproveResponseDto;
import zerobase18.playticketing.payment.dto.kakao.KakaoReadyRequestDto;
import zerobase18.playticketing.payment.dto.kakao.KakaoReadyResponseDto;
import zerobase18.playticketing.payment.type.KakaoConstants;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private KakaoReadyRequestDto kakaoReadyRequest;
    private String tid;

    @Value("${kakao-secret-key}")
    private String secretKey;

    // 결제 준비
    public KakaoReadyResponseDto kakaoPaymentReady(KakaoReadyRequestDto kakaoReadyRequestDto){
        log.info("[Service] paymentReady!");
        // 결제 승인에서 사용하기 위해 결제 준비 요청 값 담아주기
        kakaoReadyRequest = kakaoReadyRequestDto;

        JSONObject jsonObject = getJsonObject(kakaoReadyRequestDto);
        HttpEntity<String> paymentReadyRequest = new HttpEntity<>(jsonObject.toString(),getHeaders());
        // 결제 준비 요청후 응답
        KakaoReadyResponseDto kakaoReadyResponseDto = restTemplate.postForObject(KakaoConstants.KAKAO_PAYMENT_READY_URL,
                paymentReadyRequest, KakaoReadyResponseDto.class);
        // 결제 승인에서 사용할 tid 값 담아주기
        tid = kakaoReadyResponseDto.getTid();

        return kakaoReadyResponseDto;
    }

    // 결제 승인
    public KakaoApproveResponseDto kakaoPaymentApprove(String pg_token){
        log.info("[Service] paymentApprove!");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid",kakaoReadyRequest.getCid());
        jsonObject.put("tid",tid);
        jsonObject.put("partner_order_id",kakaoReadyRequest.getPartner_order_id());
        jsonObject.put("partner_user_id",kakaoReadyRequest.getPartner_user_id());
        jsonObject.put("pg_token",pg_token);

        HttpEntity<String> kakaoApproveRequest = new HttpEntity<>(jsonObject.toString(),getHeaders());

        // 결제승인 요청후 응답
        return restTemplate.postForObject(KakaoConstants.KAKAO_PAYMENT_APPROVE_URL,
                kakaoApproveRequest, KakaoApproveResponseDto.class);
    }

    // JSON Object에 담아주기
    private static JSONObject getJsonObject(KakaoReadyRequestDto kakaoReadyRequestDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid", kakaoReadyRequestDto.getCid());
        jsonObject.put("partner_order_id", kakaoReadyRequestDto.getPartner_order_id());
        jsonObject.put("partner_user_id", kakaoReadyRequestDto.getPartner_user_id());
        jsonObject.put("item_name", kakaoReadyRequestDto.getItem_name());
        jsonObject.put("quantity", kakaoReadyRequestDto.getQuantity());
        jsonObject.put("total_amount", kakaoReadyRequestDto.getTotal_amount());
        jsonObject.put("tax_free_amount", kakaoReadyRequestDto.getTax_free_amount());
        jsonObject.put("approval_url", kakaoReadyRequestDto.getApproval_url());
        jsonObject.put("cancel_url", kakaoReadyRequestDto.getCancel_url());
        jsonObject.put("fail_url", kakaoReadyRequestDto.getFail_url());
        return jsonObject;
    }

    // 헤더값 설정하기
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Host",KakaoConstants.KAKAO_HOST);
        headers.set("Authorization",secretKey);
        headers.set("Content-Type",KakaoConstants.KAKAO_CONTENT_TYPE);
        return headers;
    }
}
