package site.connectdots.connectdotsprj.global.exception.custom;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"errorCode", "message"})
public class ExceptionResponse<T extends CustomException, V> {
    private CustomErrorCode errorCode;
    private String message;
    private V payload;

    public ExceptionResponse(T customException) {
        this.errorCode = customException.getErrorCode();
        this.message = customException.getMessage();
        this.payload = null;
    }

    public ExceptionResponse(T customException, V payload) {
        this.errorCode = customException.getErrorCode();
        this.message = customException.getMessage();
        this.payload = payload;
    }

}
