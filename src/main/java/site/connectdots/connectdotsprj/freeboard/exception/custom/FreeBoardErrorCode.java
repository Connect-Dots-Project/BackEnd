package site.connectdots.connectdotsprj.freeboard.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.connectdots.connectdotsprj.global.exception.custom.CustomErrorCode;

@Getter
@RequiredArgsConstructor
public enum FreeBoardErrorCode implements CustomErrorCode {
    /**
     * 404 - NOT_FOUND
     */
    NOT_FOUND_FREE_BOARD(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
    UNAUTHORIZED_LIKE_AND_HATE_EXCEPTION(HttpStatus.BAD_REQUEST, "본인의 글은 좋아요/싫어요가 불가능합니다."),
    UNAUTHORIZED_MODIFICATION_EXCEPTION(HttpStatus.BAD_REQUEST, "글 작성자가 아니면 수정할 수 없습니다."),
    UNAUTHORIZED_DELETION_EXCEPTION(HttpStatus.BAD_REQUEST, "글 작성자가 아니면 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
