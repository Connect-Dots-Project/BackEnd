package site.connectdots.connectdotsprj.freeboard.dto.response;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardReplyMemberDTO {

    private String memberNickname;
    private String memberProfile;
}
