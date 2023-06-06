package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotifyTokenDTO {

    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private String scope;
}
