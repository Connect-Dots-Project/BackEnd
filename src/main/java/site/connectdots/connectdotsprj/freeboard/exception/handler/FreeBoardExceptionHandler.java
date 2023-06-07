package site.connectdots.connectdotsprj.freeboard.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.connectdots.connectdotsprj.freeboard.exception.custom.FreeBoardErrorResponse;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundMemberException;

@Slf4j
@RestControllerAdvice
public class FreeBoardExceptionHandler {

    @ExceptionHandler(NotFoundFreeBoardException.class)
    public FreeBoardErrorResponse<?> notFoundFreeBoardException(NotFoundFreeBoardException e) {
        log.error("NotFoundFreeBoardException : \terrorCode : {}\tmessage : {}\tfreeBoardIdx : {}", e.getErrorCode(), e.getErrorCode().getMessage(), e.getFreeBoardIdx());
        return new FreeBoardErrorResponse<>(e);
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public FreeBoardErrorResponse<?> notFoundMemberException(NotFoundMemberException e) {
        log.error("NotFoundMemberException : \terrorCode : {}\tmessage : {}\tfreeBoardIdx : {}", e.getErrorCode(), e.getErrorCode().getMessage(), e.getMemberIdx());
        return new FreeBoardErrorResponse<>(e);
    }

}
