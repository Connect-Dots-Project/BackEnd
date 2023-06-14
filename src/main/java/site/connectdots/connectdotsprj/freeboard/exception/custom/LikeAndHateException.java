package site.connectdots.connectdotsprj.freeboard.exception.custom;

import site.connectdots.connectdotsprj.global.exception.custom.CustomException;

public class LikeAndHateException extends CustomException {

    public LikeAndHateException(FreeBoardErrorCode errorCode) {
        super(errorCode);
    }
}
