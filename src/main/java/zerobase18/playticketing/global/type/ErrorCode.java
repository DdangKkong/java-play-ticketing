package zerobase18.playticketing.global.type;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * common error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),


    /**
     * customer, seller, admin
     */
    ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자를 찾을 수 없습니다."),
    ALREADY_USE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    USER_INVALID(HttpStatus.BAD_REQUEST, "잘못된 유저 정보입니다."),
    COMPANY_INVALID(HttpStatus.BAD_REQUEST, "잘못된 연극업체 정보입니다."),
    UN_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 고객 입니다."),
    TROUPE_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 연극업체 정보입니다."),

    /**
     * play
     */
    SCHEDULE_DATE_INVALID(HttpStatus.BAD_REQUEST, "잘못된 연극 스케줄의 날짜입니다."),
    SCHEDULE_TIME_INVALID(HttpStatus.BAD_REQUEST, "잘못된 연극 스케줄의 시간입니다."),
    THEATER_CONNECT_DENIED(HttpStatus.FORBIDDEN, "접근할 수 없는 극장 정보입니다."),
    PLAY_INVALID(HttpStatus.BAD_REQUEST, "잘못된 연극 정보입니다."),

    /**
     * theater
     */
    THEATER_INVALID(HttpStatus.BAD_REQUEST, "잘못된 극장 정보입니다."),
    SEAT_CONFLICT(HttpStatus.CONFLICT, "이미 생성된 좌석 정보입니다."),

    /**
     * review
     */
    CUSTOMER_AUTHORITY_NOT_MATCH(HttpStatus.BAD_REQUEST, "고객 정보가 일치하지 않습니다."),
    ALREADY_EXIST_REVIEW(HttpStatus.BAD_REQUEST, "이미 리뷰가 존재합니다."),


    /**
     * Q&A
     */
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "질문을 찾을 수 없습니다."),
    QUESTION_NOT_MATCH(HttpStatus.BAD_REQUEST, "질문과 사용자가 일치하지 않습니다."),
    ALREADY_ANSWER_QUESTION(HttpStatus.BAD_REQUEST, "이미 답변이 등록된 질문입니다."),

    REVIEW_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "리뷰를 등록할 권한이 없습니다."),
  
    /**
     * payment
     */
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 예약 정보가 없습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 결제 정보가 존재하지 않습니다."),
    RESERVATION_NOT_APPLY(HttpStatus.BAD_REQUEST, "예약 신청이어야만 결제 가능합니다."),
    ALREADY_CANCEL_PAYMENT(HttpStatus.BAD_REQUEST, "이미 결제 취소한 상태입니다."),
    TOO_OLD_RESERVATION(HttpStatus.BAD_REQUEST, "연극 상영일자가 당일이거나 지났습니다."),
    ZERO_CANCEL_AMOUNT(HttpStatus.BAD_REQUEST, "환불 받을 금액이 0원입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String description;
}
