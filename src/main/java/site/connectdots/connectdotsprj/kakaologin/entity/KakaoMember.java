package site.connectdots.connectdotsprj.kakaologin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class KakaoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kakaoIdx;
    private String kakaoNickname;
    private String kakaoProfileImage;
    private String KakaoEmail;
}
