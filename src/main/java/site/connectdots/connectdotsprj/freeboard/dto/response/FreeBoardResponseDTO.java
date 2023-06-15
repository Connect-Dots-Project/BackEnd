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

    private Long freeBoardIdx; //{freeBoardIdx}
    private String freeBoardTitle; //{freeBoardTitle}
    //    private String freeBoardContent;
    private String freeBoardImg; //{freeBoardImg}
    private String freeBoardCategory; //{freeBoardCategory}
    private String freeBoardLocation; //{freeBoardLocation}
    private String freeBoardWriteDate; //{freeBoardWriteDate}
    private String freeBoardUpdateDate; //{freeBoardUpdateDate}
    //    private Long freeBoardViewCount;
    private Long freeBoardReplyCount; //{freeBoardReplyCount}
    private Long freeBoardLikeCount; //{freeBoardLikeCount}
    private Long memberIdx; // {freeBoardMemberIdx}

    public FreeBoardResponseDTO(FreeBoard freeBoard) {
        this.freeBoardIdx = freeBoard.getFreeBoardIdx();
        this.freeBoardTitle = freeBoard.getFreeBoardTitle();
        this.freeBoardImg = freeBoard.getFreeBoardImg();
        this.freeBoardWriteDate = dateFormat(freeBoard.getFreeBoardWriteDate());
        this.freeBoardUpdateDate = dateFormat(freeBoard.getFreeBoardUpdateDate());
        this.freeBoardCategory = freeBoard.getFreeBoardCategory().toString();
//        this.freeBoardContent = freeBoard.getFreeBoardContent();
//        this.freeBoardViewCount = freeBoard.getFreeBoardViewCount();
        this.freeBoardReplyCount = freeBoard.getFreeBoardReplyCount();
        this.freeBoardLikeCount = freeBoard.getFreeBoardLikeCount();
        this.freeBoardLocation = freeBoard.getFreeBoardLocation();
        this.memberIdx = freeBoard.getMember().getMemberIdx();
    }

    private String dateFormat(final LocalDateTime date) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
