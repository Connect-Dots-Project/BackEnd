package site.connectdots.connectdotsprj.freeboard.exception.custom;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"errorCode", "message"})
public class FreeBoardErrorResponse<T> {
    private FreeBoardErrorCode errorCode;
    private String message;
    private T payload;

    public FreeBoardErrorResponse(FreeBoardErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public FreeBoardErrorResponse(NotFoundFreeBoardException e) {
        this.errorCode = e.getErrorCode();
        this.message = e.getMessage();
        this.payload = (T) e.getFreeBoardIdx();
    }

}
