package site.connectdots.connectdotsprj.freeboard.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardResponseDTO {

    private Long freeBoardIdx;
    private String freeBoardTitle;
    private String freeBoardContent;
    private String freeBoardImg;
    private String freeBoardCategory;
    private String freeBoardLocation;
    private String freeBoardWriteDate;
    private String freeBoardUpdateDate;
    private Long freeBoardViewCount;
    private Long freeBoardReplyCount;
    private Long freeBoardLikeCount;
    private Long memberIdx;

    public FreeBoardResponseDTO(FreeBoard freeBoard) {
        this.freeBoardIdx = freeBoard.getFreeBoardIdx();
        this.freeBoardTitle = freeBoard.getFreeBoardTitle();
        this.freeBoardContent = freeBoard.getFreeBoardContent();
        this.freeBoardImg = freeBoard.getFreeBoardImg();
        this.freeBoardWriteDate = dateFormat(freeBoard.getFreeBoardWriteDate());
        this.freeBoardUpdateDate = dateFormat(freeBoard.getFreeBoardUpdateDate());
        this.freeBoardCategory = freeBoard.getFreeBoardCategory().toString();
        this.freeBoardViewCount = freeBoard.getFreeBoardViewCount();

        this.freeBoardReplyCount = freeBoard.getFreeBoardReplyCount();

        this.freeBoardLikeCount = freeBoard.getFreeBoardLikeCount();
        this.freeBoardLocation = freeBoard.getFreeBoardLocation();
        this.memberIdx = freeBoard.getMember().getMemberIdx();
    }

    private String dateFormat(final LocalDateTime date) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
