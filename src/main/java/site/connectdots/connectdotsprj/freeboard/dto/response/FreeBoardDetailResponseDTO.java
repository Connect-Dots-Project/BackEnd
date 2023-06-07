package site.connectdots.connectdotsprj.freeboard.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.member.entity.Member;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardDetailResponseDTO {
    private FreeBoardResponseDTO freeBoardListResponseDTO;
    private String memberNickname;
    private String memberProfile;

    public FreeBoardDetailResponseDTO(FreeBoard freeBoard) {
        this.freeBoardListResponseDTO = new FreeBoardResponseDTO(freeBoard);
        this.memberNickname = freeBoard.getMember().getMemberNickname();
        this.memberProfile = freeBoard.getMember().getMemberProfile();
    }

}
