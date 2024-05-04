package zerobase18.playticketing.global.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase18.playticketing.global.dto.ErrorResponse;

import static zerobase18.playticketing.global.type.ErrorCode.INTERNAL_SERVER_ERROR;
import static zerobase18.playticketing.global.type.ErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ErrorResponse customExceptionHandler(CustomException e){
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse dataIntegrityViolationException(DataIntegrityViolationException e){

        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse usernameNotFoundException(UsernameNotFoundException e){
        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription());
    }

//    @ExceptionHandler(Exception.class)
//    public ErrorResponse exceptionHandler(Exception e){
//        return new ErrorResponse(INTERNAL_SERVER_ERROR,
//                INTERNAL_SERVER_ERROR.getDescription());
//    }
}
