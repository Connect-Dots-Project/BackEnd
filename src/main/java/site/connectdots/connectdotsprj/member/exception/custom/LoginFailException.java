package site.connectdots.connectdotsprj.member.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

@Getter
public class LoginFailException extends CustomException {

    public LoginFailException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
