package site.connectdots.connectdotsprj.kakaologin.dto;

import lombok.Getter;

@Getter
public class KakaoLoginRequestDTO {

    private String kakaoAppkey; //Rest API키
    private String kakoRedirectURI;
    private String kakaoCode;


}
