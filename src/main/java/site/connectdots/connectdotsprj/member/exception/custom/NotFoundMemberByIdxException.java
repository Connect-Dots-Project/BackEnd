package site.connectdots.connectdotsprj.member.exception.custom;

import lombok.Getter;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

public class NotFoundMemberByIdxException extends CustomException {
    @Getter
    private final Long memberIdx;

    public NotFoundMemberByIdxException(CustomErrorCode errorCode, Long memberIdx) {
        super(errorCode);
        this.memberIdx = memberIdx;
    }

}
