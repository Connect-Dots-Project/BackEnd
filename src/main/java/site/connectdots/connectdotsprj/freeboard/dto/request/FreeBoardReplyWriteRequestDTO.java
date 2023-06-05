package site.connectdots.connectdotsprj.freeboard.dto.request;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.member.entity.Member;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardReplyWriteRequestDTO {

    private String freeBoardReplyContent;
    private Long memberIdx;
    private Long freeBoardIdx;

    public FreeBoardReply toEntity(FreeBoard freeBoard, Member member) {
        return FreeBoardReply.builder()
                .freeBoard(freeBoard)
                .member(member)
                .freeBoardReplyContent(this.freeBoardReplyContent)
                .build();
    }

}
