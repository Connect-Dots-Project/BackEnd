package site.connectdots.connectdotsprj.musicboard.dto.response;

import site.connectdots.connectdotsprj.musicboard.entity.Music;

public class LoginSpotifyDTO {

    private String clientId;
    private String clientSecret;
    private String redirectUri;

    //dto를 entity로 변환
    public Music toEntity(){
        return Music.builder()
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .redirectUri(this.redirectUri)
                .build();
    }
}
