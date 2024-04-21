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
    UN_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 고객 입니다."),


    /**
     * reservation
     */

    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "예약을 찾을 수 없습니다."),


    /**
     * review
     */
    CUSTOMER_AUTHORITY_NOT_MATCH(HttpStatus.BAD_REQUEST, "고객 정보가 일치하지 않습니다."),
    ALREADY_EXIST_REVIEW(HttpStatus.BAD_REQUEST, "이미 리뷰가 존재합니다."),
    REVIEW_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "리뷰를 등록할 권한이 없습니다."),

    /**
     * Q&A
     */
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "질문을 찾을 수 없습니다."),
    QUESTION_NOT_MATCH(HttpStatus.BAD_REQUEST, "질문과 사용자가 일치하지 않습니다."),
    ALREADY_ANSWER_QUESTION(HttpStatus.BAD_REQUEST, "이미 답변이 등록된 질문입니다.");

    private final HttpStatus httpStatus;
    private final String description;
}
