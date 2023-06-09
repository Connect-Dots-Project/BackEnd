package site.connectdots.connectdotsprj.freeboard.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardDetailReplyDTO {
    private Long freeBoardReplyIdx;
    private String freeBoardReplyCreateDate;
    private String freeBoardReplyContent;

    private FreeBoardReplyMemberDTO freeBoardReplyMemberDTO;

    public FreeBoardDetailReplyDTO(FreeBoardReply freeBoardReply) {
        this.freeBoardReplyIdx = freeBoardReply.getFreeBoardReplyIdx();
        this.freeBoardReplyCreateDate = freeBoardReply.getFreeBoardReplyCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.freeBoardReplyContent = freeBoardReply.getFreeBoardReplyContent();
        this.freeBoardReplyMemberDTO = FreeBoardReplyMemberDTO.builder()
                .memberNickname(freeBoardReply.getMember().getMemberNickname())
                .memberProfile(freeBoardReply.getMember().getMemberProfile())
                .build();

    }
}
