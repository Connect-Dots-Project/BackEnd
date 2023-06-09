package site.connectdots.connectdotsprj.musicboard.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MusicErrorCode {

    /**
     * 404 - NOT_FOUND
     */
    NOT_FOUND_MEMBER_ERROR(HttpStatus.BAD_REQUEST, "존재하지 않는 게시물입니다.");

    private final HttpStatus httpStatus;
    private final String message;
    }
