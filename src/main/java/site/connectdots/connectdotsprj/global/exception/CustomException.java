package site.connectdots.connectdotsprj.global.exception;

import lombok.Getter;
import site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorCode;

public class CustomException extends RuntimeException {
    @Getter
    private final FreeBoardErrorCode errorCode;

    public CustomException(FreeBoardErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
