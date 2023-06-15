package site.connectdots.connectdotsprj.member.dto.request;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginRequestDTO {

    private String account;
    private String password;
    private Boolean isAutoLogin;

}
