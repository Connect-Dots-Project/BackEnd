package site.connectdots.connectdotsprj.member.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;
import site.connectdots.connectdotsprj.global.exception.custom.ExceptionResponse;
import site.connectdots.connectdotsprj.member.exception.custom.SignUpFailException;

@RestControllerAdvice
@Slf4j
public class MemberSignUpHandler {

    @ExceptionHandler(SignUpFailException.class)
    public ExceptionResponse<? extends CustomException, String> signUpFailException(SignUpFailException e) {
        log.error("SignUpFailException {} --- {}", e.getErrorCode(), e.getMessage());
        return new ExceptionResponse<>(e, e.getPayload());
    }
}
