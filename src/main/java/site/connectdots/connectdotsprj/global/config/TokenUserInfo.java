package site.connectdots.connectdotsprj.global.config;


import lombok.*;
import site.connectdots.connectdotsprj.member.entity.MemberLoginMethod;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUserInfo {

    private String account;
    private String memberLoginMethod;

}
