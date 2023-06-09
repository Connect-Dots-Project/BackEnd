package site.connectdots.connectdotsprj.freeboard.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

public class NotFoundMemberException extends CustomException {
    @Getter
    private final Long memberIdx;

    public NotFoundMemberException(FreeBoardErrorCode errorCode, Long memberIdx) {
        super(errorCode);
        this.memberIdx = memberIdx;
    }
}
