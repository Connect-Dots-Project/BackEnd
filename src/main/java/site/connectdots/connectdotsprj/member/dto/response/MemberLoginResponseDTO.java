package site.connectdots.connectdotsprj.member.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.member.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MemberLoginResponseDTO {

    private String email;


    public MemberLoginResponseDTO(Member member) {
        this.email = member.getMemberAccount();
    }
}
