package site.connectdots.connectdotsprj.member.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

@Getter
public class SignUpFailException extends CustomException {

    private final String payload;

    public SignUpFailException(CustomErrorCode errorCode, String payload) {
        super(errorCode);
        this.payload = payload;
    }
}
