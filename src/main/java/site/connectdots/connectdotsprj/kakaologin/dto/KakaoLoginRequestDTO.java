package site.connectdots.connectdotsprj.kakaologin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoLoginRequestDTO {

    private String kakaoAppkey; //Rest API키
    private String kakoRedirectURI;
    private String kakaoCode;

    public KakaoLoginRequestDTO(String kakaoAppkey, String kakoRedirectURI, String kakaoCode) {
        this.kakaoAppkey = kakaoAppkey;
        this.kakoRedirectURI = kakoRedirectURI;
        this.kakaoCode = kakaoCode;
    }

}
