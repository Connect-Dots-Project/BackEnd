package site.connectdots.connectdotsprj.freeboard.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

public class UnauthorizedModificationException extends CustomException {

    @Getter
    private String account;

    public UnauthorizedModificationException(CustomErrorCode e, String account) {
        super(e);
        this.account = account;
    }

}
