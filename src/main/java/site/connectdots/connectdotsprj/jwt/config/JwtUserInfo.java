package site.connectdots.connectdotsprj.jwt.config;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class JwtUserInfo {
    private String account;
}
