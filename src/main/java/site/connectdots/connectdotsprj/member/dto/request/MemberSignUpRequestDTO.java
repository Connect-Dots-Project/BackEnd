package site.connectdots.connectdotsprj.member.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.connectdots.connectdotsprj.member.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;


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

}
