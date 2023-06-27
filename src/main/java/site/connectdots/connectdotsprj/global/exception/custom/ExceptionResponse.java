package site.connectdots.connectdotsprj.global.exception.custom;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"errorCode", "message"})
public class ExceptionResponse<T extends CustomException, V> {
    private CustomErrorCode errorCode;
    private HttpStatus httpStatus;
    private Integer httpStatusValue;
    private String message;
    private V payload;

    public ExceptionResponse(T customException) {
        this.errorCode = customException.getErrorCode();
        this.httpStatus = errorCode.getHttpStatus();
        this.httpStatusValue = errorCode.getHttpStatus().value();
        this.message = customException.getMessage();
        this.payload = null;
    }

    public ExceptionResponse(T customException, V payload) {
        this.errorCode = customException.getErrorCode();
        this.httpStatus = errorCode.getHttpStatus();
        this.httpStatusValue = errorCode.getHttpStatus().value();
        this.message = customException.getMessage();
        this.payload = payload;
    }

}
