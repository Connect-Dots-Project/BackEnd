package site.connectdots.connectdotsprj.memberemail.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestControllerAdvice
@Slf4j
public class MemberEmailExceptionHandler {
    @ExceptionHandler({MessagingException.class, UnsupportedEncodingException.class})
    public String memberEmailExceptionHandler() {
        return "email 전송에 실패했습니다.";
    }

}
