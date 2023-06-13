package site.connectdots.connectdotsprj.member.dto.request;


import lombok.*;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.MemberLoginMethod;

import java.time.LocalDate;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignUpRequestDTO {
    private String account;
    private String firstPassword;
    private String secondPassword;
    private String name;
    private String nickName;
    private LocalDate birthDay;
    private Gender gender;
    private String phone;
    private String location;
    private String comment;
    private MemberLoginMethod loginMethod;

}
