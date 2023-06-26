package site.connectdots.connectdotsprj.mypage.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceListResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageBasicDTO {

    private String memberNickname;
    private String memberProfile;
    private String memberComment;
    private List<HotplaceDetailResponseDTO> hotplaceDetailResponseDTOList;

    public MyPageBasicDTO(Member member) {
        this.memberNickname = member.getMemberNickname();
        this.memberProfile = member.getMemberProfile();
        this.memberComment = member.getMemberComment();
    }

}
