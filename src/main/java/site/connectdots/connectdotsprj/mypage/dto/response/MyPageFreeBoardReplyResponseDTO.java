package site.connectdots.connectdotsprj.mypage.dto.response;


import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageFreeBoardReplyResponseDTO {
    private String content;
    private String category;
    private String location;
    private String freeBoardTitle;
    private String freeBoardWriteTime;

    public MyPageFreeBoardReplyResponseDTO(FreeBoard freeBoard, FreeBoardReply freeBoardReply) {
        this.content = freeBoardReply.getFreeBoardReplyContent();
        this.category = String.valueOf(freeBoard.getFreeBoardCategory());
        this.location = freeBoard.getFreeBoardLocation();
        this.freeBoardTitle = freeBoard.getFreeBoardTitle();
        this.freeBoardWriteTime =
                freeBoard.getFreeBoardWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
