package site.connectdots.connectdotsprj.hotplace.dto.responseDTO;

import lombok.*;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceMemberDTO {

    private String memberAccount;
    private String memberNickname;

    public HotplaceMemberDTO(Member member) {
        this.memberAccount = member.getMemberAccount();
        this.memberNickname = member.getMemberNickname();
    }

}
