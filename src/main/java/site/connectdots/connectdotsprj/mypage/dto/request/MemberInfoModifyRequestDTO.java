package site.connectdots.connectdotsprj.mypage.dto.request;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoModifyRequestDTO {

    private String inputMemberNickname;
    private String inputMemberComment;
}
