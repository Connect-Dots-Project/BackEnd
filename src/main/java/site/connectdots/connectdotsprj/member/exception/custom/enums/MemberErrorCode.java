package site.connectdots.connectdotsprj.member.exception.custom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements CustomErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
