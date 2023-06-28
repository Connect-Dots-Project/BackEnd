package site.connectdots.connectdotsprj.member.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MemberEmailCheckResponseDTO {
    private Boolean checkEmail;
}
