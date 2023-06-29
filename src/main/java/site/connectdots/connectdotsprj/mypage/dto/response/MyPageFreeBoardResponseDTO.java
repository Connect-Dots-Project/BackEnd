package site.connectdots.connectdotsprj.mypage.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardLike;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageFreeBoardResponseDTO {

    private String category;
    private String location;
    private String title;
    private String writeTime;

    public MyPageFreeBoardResponseDTO(FreeBoard freeBoard) {
        this.category = String.valueOf(freeBoard.getFreeBoardCategory());
        this.location = freeBoard.getFreeBoardLocation();
        this.title = freeBoard.getFreeBoardTitle();
        this.writeTime = freeBoard.getFreeBoardWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
