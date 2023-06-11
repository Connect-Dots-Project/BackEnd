package site.connectdots.connectdotsprj.member.exception.custom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;

@Getter
@RequiredArgsConstructor
public enum LoginFailErrorCode implements CustomErrorCode {

    LOGIN_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
