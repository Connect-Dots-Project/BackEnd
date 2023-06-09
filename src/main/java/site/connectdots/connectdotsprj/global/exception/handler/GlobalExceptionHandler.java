package site.connectdots.connectdotsprj.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> notFoundFreeBoard() {
//        return ResponseEntity.badRequest().body("정의하지 않은 익셉션 발생");
//    }

}
