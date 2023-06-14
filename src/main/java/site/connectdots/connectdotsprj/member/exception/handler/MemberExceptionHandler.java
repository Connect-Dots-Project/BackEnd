package site.connectdots.connectdotsprj.member.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.connectdots.connectdotsprj.global.exception.custom.CustomException;
import site.connectdots.connectdotsprj.global.exception.custom.ExceptionResponse;
import site.connectdots.connectdotsprj.member.exception.custom.NotFoundMemberByAccountException;
import site.connectdots.connectdotsprj.member.exception.custom.NotFoundMemberByIdxException;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(NotFoundMemberByIdxException.class)
    public ResponseEntity<ExceptionResponse<? extends CustomException, Long>> notFoundMemberByIdxException(NotFoundMemberByIdxException e) {
        log.error("NotFoundMemberException : {} --- {}", e.getErrorCode(), e.getMemberIdx());
        return ResponseEntity.badRequest().body(new ExceptionResponse<>(e, e.getMemberIdx()));
    }

    @ExceptionHandler(NotFoundMemberByAccountException.class)
    public ResponseEntity<ExceptionResponse<? extends CustomException, String>> notFoundMemberByAccountException(NotFoundMemberByAccountException e) {
        log.error("NotFoundMemberException : {} --- {}", e.getErrorCode(), e.getMemberAccount());
        return ResponseEntity.badRequest().body(new ExceptionResponse<>(e, e.getMemberAccount()));
    }

}
