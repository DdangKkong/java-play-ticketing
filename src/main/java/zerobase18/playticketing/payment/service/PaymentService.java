package zerobase18.playticketing.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.payment.dto.PaymentDto;
import zerobase18.playticketing.payment.dto.kakao.*;
import zerobase18.playticketing.payment.dto.toss.TossApproveRequestDto;
import zerobase18.playticketing.payment.dto.toss.TossApproveResponseDto;
import zerobase18.playticketing.payment.dto.toss.TossCancelRequestDto;
import zerobase18.playticketing.payment.entity.Payment;
import zerobase18.playticketing.payment.entity.Reservation;
import zerobase18.playticketing.payment.repository.PaymentRepository;
import zerobase18.playticketing.payment.repository.ReservationRepository;
import zerobase18.playticketing.payment.type.KakaoConstants;
import zerobase18.playticketing.payment.type.ReserStat;
import zerobase18.playticketing.payment.type.TossConstants;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static zerobase18.playticketing.global.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    // 카카오 결제 요청 데이터 임시 저장
    private KakaoReadyRequestDto kakaoReadyRequest;
    private String tid;

    // 토스 결제 요청 데이터 임시 저장
    private int reserAmount;
    private String orderId;
    private int reserId;

    @Value("${kakao-secret-key}")
    private String kakaoSecretKey;
    @Value("${toss-secret-key}")
    private String tossSecretKey;

    // 카카오페이 결제 준비
    public KakaoReadyResponseDto kakaoPaymentReady(KakaoReadyRequestDto kakaoReadyRequestDto){
        log.info("[Service] kakaoPaymentReady!");
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

    // 카카오페이 결제 승인
    @Transactional
    public PaymentDto kakaoPaymentApprove(int reserId,String pg_token){
        log.info("[Service] kakaoPaymentApprove!");
        // 결제할 예약 정보 조회
        Reservation reservation = reservationRepository.findById(reserId)
                .orElseThrow(()-> new CustomException(RESERVATION_NOT_FOUND));

        // 예약 신청이어야만 결제 가능
        paymentPossible(reservation);

        // 예약 상태를 예약 완료로 변경
        reservation.successReser();

        // 결제 승인 응답
        KakaoApproveResponseDto kakaoApproveResponseDto = kakaoPaymentAccept(pg_token);

        // 응답값 변환
        Payment payment = Payment.fromKakaoApproveDto(kakaoApproveResponseDto);

        // 결제 정보에 예약 고유번호 설정
        payment.addReserId(reservation);

        // 결제 정보 저장
        return PaymentDto.fromEntity(paymentRepository.save(payment));
    }

    // 카카오페이 결제 승인 응답
    private KakaoApproveResponseDto kakaoPaymentAccept(String pg_token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid",kakaoReadyRequest.getCid());
        jsonObject.put("tid",tid);
        jsonObject.put("partner_order_id",kakaoReadyRequest.getPartner_order_id());
        jsonObject.put("partner_user_id",kakaoReadyRequest.getPartner_user_id());
        jsonObject.put("pg_token", pg_token);

        HttpEntity<String> kakaoApproveRequest = new HttpEntity<>(jsonObject.toString(),getHeaders());

        // 결제승인 요청후 응답
        return restTemplate.postForObject(KakaoConstants.KAKAO_PAYMENT_APPROVE_URL,
                kakaoApproveRequest, KakaoApproveResponseDto.class);
    }

    // 결제 준비 요청값 JSON Object에 담아주기
    private JSONObject getJsonObject(KakaoReadyRequestDto kakaoReadyRequestDto) {
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
        headers.set("Authorization",kakaoSecretKey);
        headers.set("Content-Type",KakaoConstants.KAKAO_CONTENT_TYPE);
        return headers;
    }


    // 토스 페이먼츠 결제 승인
    @Transactional
    public PaymentDto tossPaymentApprove(TossApproveRequestDto tossApproveRequestDto) {
        log.info("[Service] tossPaymentApprove!");
        // 결제할 예약 정보 조회 (결제 요청때 저장한 예약 고유번호)
        Reservation reservation = reservationRepository.findById(tossApproveRequestDto.getReserId())
                .orElseThrow(()->new CustomException(RESERVATION_NOT_FOUND)); // 해당 예약이 존재하지 않습니다.

        // 예약 신청이어야만 결제 가능
        paymentPossible(reservation);

        // 예약 상태를 예약 완료로 변경
        reservation.successReser();

        // 결제 승인 응답
        TossApproveResponseDto tossApproveResponseDto = tossPaymentAccept(tossApproveRequestDto);

        // 응답값 변환
        Payment payment = Payment.fromTossDto(tossApproveResponseDto);

        // 결제 정보에 예약 고유번호 설정
        payment.addReserId(reservation);

        // 결제 정보 저장
        return PaymentDto.fromEntity(paymentRepository.save(payment));

    }

    // 예약 신청이어야만 결제 가능
    private void paymentPossible(Reservation reservation) {
        if (reservation.getReserStat() != ReserStat.APPLY){
            throw new CustomException(RESERVATION_NOT_APPLY);
        }
    }

    // 토스 페이먼츠 결제 승인 응답
    private TossApproveResponseDto tossPaymentAccept(TossApproveRequestDto tossApproveRequestDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", tossApproveRequestDto.getOrderId());
        jsonObject.put("amount", tossApproveRequestDto.getAmount());
        jsonObject.put("paymentKey", tossApproveRequestDto.getPaymentKey());

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = tossSecretKey;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorizations);
        headers.set("Content-Type",TossConstants.TOSS_CONTENT_TYPE);

        HttpEntity<String> tossApproveRequest = new HttpEntity<>(jsonObject.toString(),headers);

        return restTemplate.postForObject(TossConstants.TOSS_PAYMENT_APPROVE_URL,
                tossApproveRequest, TossApproveResponseDto.class);
    }

    // 카카오페이 결제 취소
    @Transactional
    public PaymentDto kakaoPaymentCancel(KakaoCancelRequestDto kakaoCancelRequestDto){
        log.info("[Service] kakaoPaymentCancel!");
        // 결제 취소할 예약 정보 조회
        Reservation reservation = reservationRepository.findById(kakaoCancelRequestDto.getReserId())
                .orElseThrow(()-> new CustomException(RESERVATION_NOT_FOUND));

        // 취소할 결제 정보 조회
        Payment payment = paymentRepository.findByTidPaymentKey(kakaoCancelRequestDto.getTid())
                .orElseThrow(()-> new CustomException(PAYMENT_NOT_FOUND));

        // 예약 상태를 예약 취소로 변경
        reservation.canceledReser();

        // 환불 금액 설정
        kakaoCancelRequestDto.setCancel_amount(reservation.getCancelAmount());

        // 카카오 결제 취소 응답
        KakaoCancelResponseDto kakaoCancelResponseDto = kakaoPaymentCancelAccept(kakaoCancelRequestDto);

        //응답값 변환
        Payment cancelPayment = Payment.fromKakaoCancelDto(kakaoCancelResponseDto);

        // 취소 일시, 취소 사유, 환불 가능 금액 설정
        payment.cancel(cancelPayment.getCanceledAt(), cancelPayment.getCancelReason(),
                cancelPayment.getRefundableAmount());

        // 결제 취소 정보 저장
        return PaymentDto.fromEntity(payment);
    }

    // 카카오페이 결제 취소 응답
    private KakaoCancelResponseDto kakaoPaymentCancelAccept(KakaoCancelRequestDto kakaoCancelRequestDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid", kakaoCancelRequestDto.getCid());
        jsonObject.put("tid",kakaoCancelRequestDto.getTid());
        jsonObject.put("cancel_amount", kakaoCancelRequestDto.getCancel_amount());
        jsonObject.put("cancel_tax_free_amount", kakaoCancelRequestDto.getCancel_tax_free_amount());
        jsonObject.put("payload", kakaoCancelRequestDto.getPayload());

        HttpEntity<String> kakaoCancelRequest = new HttpEntity<>(jsonObject.toString(),getHeaders());

        return restTemplate.postForObject(KakaoConstants.KAKAO_PAYMENT_CANCEL_URL,
                kakaoCancelRequest, KakaoCancelResponseDto.class);
    }

    // 카카오페이 결제 조회
    public KakaoOrderResponseDto kakaoPaymentOrder(KakaoOrderRequestDto kakaoOrderRequestDto) {
        log.info("[Service] kakaoPaymentOrder!");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid",kakaoOrderRequestDto.getCid());
        jsonObject.put("tid",tid);

        HttpEntity<String> kakaoOrderRequest = new HttpEntity<>(jsonObject.toString(),getHeaders());

        return restTemplate.postForObject(KakaoConstants.KAKAO_PAYMENT_ORDER_URL,
                kakaoOrderRequest, KakaoOrderResponseDto.class);
    }

    // 토스 페이먼츠 결제 취소
    @Transactional
    public PaymentDto tossPaymentCancel(TossCancelRequestDto tossCancelRequestDto) {
        log.info("[Service] tossPaymentCancel!");
        // 결제 취소할 예약 정보 조회
        Reservation reservation = reservationRepository.findById(tossCancelRequestDto.getReserId())
                .orElseThrow(()-> new CustomException(RESERVATION_NOT_FOUND)); // 해당 예약 정보가 없습니다

        // 취소할 결제 정보 조회
        Payment payment = paymentRepository.findByTidPaymentKey(tossCancelRequestDto.getPaymentKey())
                .orElseThrow(()->new CustomException(PAYMENT_NOT_FOUND)); // 해당 결제 정보가 없습니다

        // 예약 상태를 예약 취소로 변경
        reservation.canceledReser();

        // 환불 금액 설정
        tossCancelRequestDto.setCancelAmount(reservation.getCancelAmount());

        // 토스 결제 취소 응답
        TossApproveResponseDto tossCancelResponseDto = tossPaymentCancelAccept(tossCancelRequestDto);

        // 응답값 변환
        Payment cancelPayment = Payment.fromTossDto(tossCancelResponseDto);

        // 취소 일시, 취소 사유, 환불 가능 금액 설정
        payment.cancel(cancelPayment.getCanceledAt(),cancelPayment.getCancelReason(),
                cancelPayment.getRefundableAmount());

        // 결제 취소 정보 저장
        return PaymentDto.fromEntity(payment);
    }

    // 토스 페이먼츠 결제 취소 응답
    private TossApproveResponseDto tossPaymentCancelAccept(TossCancelRequestDto tossCancelRequestDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cancelReason", tossCancelRequestDto.getCancelReason());
        jsonObject.put("cancelAmount", tossCancelRequestDto.getCancelAmount());

        // 가상계좌 결제 취소시 필수 값 세팅
        if (tossCancelRequestDto.getRefundReceiveAccount() != null) {
            JSONObject innerJson = new JSONObject();
            innerJson.put("bank", tossCancelRequestDto.getRefundReceiveAccount().getBank());
            innerJson.put("accountNumber", tossCancelRequestDto.getRefundReceiveAccount().getAccountNumber());
            innerJson.put("holderName", tossCancelRequestDto.getRefundReceiveAccount().getHolderName());
            jsonObject.put("refundReceiveAccount", innerJson);
        }

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = tossSecretKey;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorizations);
        headers.set("Content-Type",TossConstants.TOSS_CONTENT_TYPE);

        HttpEntity<String> tossCancelRequest = new HttpEntity<>(jsonObject.toString(),headers);

        log.info("결제 취소 요청 json");
        log.info("{}", jsonObject);

        return restTemplate.postForObject(TossConstants.TOSS_PAYMENT_URL
                        + tossCancelRequestDto.getPaymentKey() + "/cancel",
                tossCancelRequest, TossApproveResponseDto.class);
    }

    public TossApproveResponseDto tossPaymentOrder(String paymentKey) {
        log.info("[Service] tossPaymentOrder!");

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = tossSecretKey;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorizations);

        HttpEntity<String> tossOrderRequest = new HttpEntity<>(headers);

        log.info("paymentKey : {}",paymentKey);

        return restTemplate.exchange(TossConstants.TOSS_PAYMENT_URL+paymentKey, HttpMethod.GET, tossOrderRequest,
                TossApproveResponseDto.class).getBody();

    }
    // 주문번호, 금액, 예약 고유번호 임시 저장
    public void tossPaymentRequest(int reserAmount, String orderId,int reserId) {
        this.reserAmount = reserAmount;
        this.orderId = orderId;
        this.reserId = reserId;
    }

    // 토스 결제 요청 성공
    public void tossPaymentRequestSuccess(String orderId, int amount,int reserId) {
        // 결제 요청할때 저장한 주문번호, 금액, 예약 고유번호가 맞지 않을경우 예외처리
        if (!this.orderId.equals(orderId) || this.reserAmount != amount || this.reserId != reserId){
            throw new CustomException(INVALID_REQUEST);
        }
    }
}
