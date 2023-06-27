package site.connectdots.connectdotsprj.mypage.dto.response;


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
public class MemberModifyRequestDTO {

    private String firstPassword;
    private String name;
    private String nickName;
    private String phone;
    private String location;
    private String comment;


}
