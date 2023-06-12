package site.connectdots.connectdotsprj.memberemail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEmailCheckRequestDTO {
    @Email
    private String email;
}