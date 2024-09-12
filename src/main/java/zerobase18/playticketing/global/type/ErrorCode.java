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
     * user, company, troupe
     */
    ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자를 찾을 수 없습니다."),
    ALREADY_USE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    USER_INVALID(HttpStatus.BAD_REQUEST, "잘못된 유저 정보입니다."),
    SELLER_INVALID(HttpStatus.BAD_REQUEST, "잘못된 연극업체 정보입니다."),
    UN_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 고객 입니다."),
    TROUPE_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 연극업체 정보입니다."),
    COMPANY_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 극장업체입니다."),
    USER_LOCK(HttpStatus.BAD_REQUEST, "계정이 비활성화 되었습니다. 관리자에게 문의해주세요."),
    CUSTOMER_EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "계정과 이메일이 일치하지 않습니다."),
    USER_ALREADY_ACTIVE(HttpStatus.BAD_REQUEST, "계정이 이미 활성화 상태입니다."),
    CONFIRM_EMAIL_AUTH(HttpStatus.BAD_REQUEST, "이메일 인증을 확인해주세요."),
    PRECEED_SIGNUP(HttpStatus.BAD_REQUEST, "회원 가입을 먼저 진행해주세요."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다"),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 인증 코드입니다."),

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
     * schedule
     */

    SCHEDULE_NOT_FOUND(HttpStatus.CONFLICT, "스케줄이 존재하지 않습니다."),


    /**
     * schedule_seat
     */

    SCHEDULE_SEAT_NOT_FOUND(HttpStatus.CONFLICT, "스케줄별 좌석이 존재하지 않습니다."),
    ALREADY_RESERVATION_SEAT(HttpStatus.CONFLICT, "이미 예약된 좌석입니다."),
    NOT_RESERVATION_SEAT(HttpStatus.CONFLICT, "예약되어 있지 않은 좌석입니다."),

    /**
     * review, comment
     */
    CUSTOMER_AUTHORITY_NOT_MATCH(HttpStatus.BAD_REQUEST, "고객 정보가 일치하지 않습니다."),
    ALREADY_EXIST_REVIEW(HttpStatus.BAD_REQUEST, "이미 리뷰가 존재합니다."),
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "리뷰를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다."),
    CUSTOMER_NOT_MATCH(HttpStatus.BAD_REQUEST, "작성자를 찾을 수 없습니다."),

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
    INVALID_CANCEL_AMOUNT(HttpStatus.BAD_REQUEST, "잘못된 환불 금액입니다."),


    /**
     * security error
     */
    WRONG_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다."),
    TOKEN_TIME_OUT(HttpStatus.CONFLICT, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 토큰입니다."),
    JWT_TOKEN_WRONG_TYPE(HttpStatus.UNAUTHORIZED, "유효하지 않은 구성의 JWT 토큰입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 되지 않았습니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다.");


    private final HttpStatus httpStatus;
    private final String description;
}
