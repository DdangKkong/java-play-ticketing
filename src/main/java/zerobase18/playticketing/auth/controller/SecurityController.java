package zerobase18.playticketing.auth.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.global.exception.CustomException;

import static zerobase18.playticketing.global.type.ErrorCode.INVALID_ACCESS_TOKEN;
import static zerobase18.playticketing.global.type.ErrorCode.JWT_TOKEN_WRONG_TYPE;

@RestController
public class SecurityController {

    @GetMapping("/exception/accessDenied")
    public void accessDenied() {
        throw new CustomException(INVALID_ACCESS_TOKEN);
    }

    @GetMapping("/exception/unaccessDenied")
    public void unAccessDenied() {
        throw new CustomException(JWT_TOKEN_WRONG_TYPE);
    }

}
