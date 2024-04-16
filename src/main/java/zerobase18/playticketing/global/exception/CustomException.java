package zerobase18.playticketing.global.exception;


import lombok.Getter;
import zerobase18.playticketing.global.type.ErrorCode;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String errorMessage;


    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDescription());

        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

}
