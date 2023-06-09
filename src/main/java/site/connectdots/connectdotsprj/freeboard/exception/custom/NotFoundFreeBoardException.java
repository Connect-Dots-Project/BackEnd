package site.connectdots.connectdotsprj.freeboard.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

public class NotFoundFreeBoardException extends CustomException {
    @Getter
    private final Long freeBoardIdx;

    public NotFoundFreeBoardException(FreeBoardErrorCode errorCode, Long freeBoardIdx) {
        super(errorCode);
        this.freeBoardIdx = freeBoardIdx;
    }
}
