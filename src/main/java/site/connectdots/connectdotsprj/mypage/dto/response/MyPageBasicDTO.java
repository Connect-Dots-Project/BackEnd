package site.connectdots.connectdotsprj.mypage.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceListResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String memberAccount;
    private String memberNickname;
    private Gender memberGender;
    private String memberProfile;
    private String memberComment;
    private String memberBirth;

    public MyPageBasicDTO(Member member) {
        this.memberAccount = member.getMemberAccount();
        this.memberNickname = member.getMemberNickname();
        this.memberGender = member.getMemberGender();
        this.memberProfile = member.getMemberProfile();
        this.memberComment = member.getMemberComment();
        this.memberBirth = changeFormatter(member.getMemberBirth());
    }

    private String changeFormatter(LocalDateTime target) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return formatter.format(target);
    }

}
