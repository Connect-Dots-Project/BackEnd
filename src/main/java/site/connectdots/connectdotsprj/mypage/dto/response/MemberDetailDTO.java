package site.connectdots.connectdotsprj.mypage.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.member.entity.Member;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailDTO {

    private Long memberIdx;
    private String memberAccount;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private String memberProfile;
    private String memberPhone;
    private String memberLocation;
    private String memberComment;


    // 수정이 필요없는항목인데 넣어야할까?
    //    private Gender memberGender;
    //    private LocalDateTime memberBirth;


    public MemberDetailDTO(Member member) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberIdx(member.getMemberIdx());
        memberDetailDTO.setMemberAccount(member.getMemberAccount());
        memberDetailDTO.setMemberPassword(member.getMemberPassword());
        memberDetailDTO.setMemberName(member.getMemberName());
        memberDetailDTO.setMemberNickname(member.getMemberNickname());
        memberDetailDTO.setMemberProfile(member.getMemberProfile());
        memberDetailDTO.setMemberPhone(member.getMemberPhone());
        memberDetailDTO.setMemberLocation(member.getMemberLocation());
        memberDetailDTO.setMemberComment(member.getMemberComment());


    }
}
