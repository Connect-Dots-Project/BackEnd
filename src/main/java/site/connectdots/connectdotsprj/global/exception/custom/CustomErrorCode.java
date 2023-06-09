package site.connectdots.connectdotsprj.global.exception.custom;

import org.springframework.http.HttpStatus;

public interface CustomErrorCode {

    String getMessage();

    HttpStatus getHttpStatus();
}
