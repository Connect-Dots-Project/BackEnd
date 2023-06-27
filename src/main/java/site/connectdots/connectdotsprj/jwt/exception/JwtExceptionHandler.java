package site.connectdots.connectdotsprj.jwt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class JwtExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ResponseEntity<?> tokenException(T payload) {
        log.error("tokenException - {}", payload);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(payload);
    }
}
