package site.connectdots.connectdotsprj.member.dto.request;

import lombok.*;

import javax.validation.constraints.Email;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEmailCheckRequestDTO {
    @Email
    private String email;

}
