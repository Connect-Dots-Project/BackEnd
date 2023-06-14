package site.connectdots.connectdotsprj.member.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

public class NotFoundMemberByAccountException extends CustomException {
    @Getter
    private final String memberAccount;

    public NotFoundMemberByAccountException(CustomErrorCode errorCode, String memberAccount) {
        super(errorCode);
        this.memberAccount = memberAccount;
    }

}
