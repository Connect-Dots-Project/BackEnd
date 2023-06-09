package site.connectdots.connectdotsprj.global.exception.custom;

import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private final CustomErrorCode errorCode;

    public CustomException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
