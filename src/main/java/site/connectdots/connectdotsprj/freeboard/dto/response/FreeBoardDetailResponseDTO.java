package site.connectdots.connectdotsprj.freeboard.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardDetailResponseDTO {

    private FreeBoardResponseDTO freeBoardResponseDTO;
    private String memberNickname;
    private String memberProfile;
    private List<FreeBoardDetailReplyDTO> replyList;
    private String loginMemberAccount;
    private String loginMemberProfile;

    public FreeBoardDetailResponseDTO(FreeBoard freeBoard, List<FreeBoardDetailReplyDTO> replyList, Member member) {
        this.freeBoardResponseDTO = new FreeBoardResponseDTO(freeBoard);
        this.memberNickname = freeBoard.getMember().getMemberNickname();
        this.memberProfile = freeBoard.getMember().getMemberProfile();
        this.freeBoardResponseDTO.setFreeBoardLikeCount(freeBoard.getFreeBoardLikeCount());
        this.freeBoardResponseDTO.setFreeBoardReplyCount((long) replyList.size());
        this.loginMemberAccount = member.getMemberAccount();
        this.loginMemberProfile = member.getMemberProfile();
        this.replyList = replyList;
    }

}
