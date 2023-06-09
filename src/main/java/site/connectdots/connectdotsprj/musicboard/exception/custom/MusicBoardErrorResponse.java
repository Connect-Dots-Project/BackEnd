package site.connectdots.connectdotsprj.musicboard.exception.custom;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"errorCode", "message"})
public class MusicBoardErrorResponse<T> {
    private MusicErrorCode errorCode;
    private String message;
    private T payload;



}
