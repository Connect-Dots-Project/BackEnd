package site.connectdots.connectdotsprj.member.exception.custom;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;

@Getter
@RequiredArgsConstructor
public enum SignUpFailErrorCode implements CustomErrorCode {

    VALIDATE_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, "첫번째 비밀번호와 두번째 비밀번호가 다릅니다."),
    DUPLICATE_ACCOUNT(HttpStatus.BAD_REQUEST, "이메일이 중복되었습니다."),
    VALIDATE_LOCATION_EXCEPTION(HttpStatus.BAD_REQUEST, "지역 정보가 잘 못 되었습니다."),
    DUPLICATE_NICK_NAME_EXCEPTION(HttpStatus.BAD_REQUEST, "닉네임이 중복되었습니다."),
    DUPLICATE_PHONE_EXCEPTION(HttpStatus.BAD_REQUEST, "핸드폰 번호가 중복되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
