package site.connectdots.connectdotsprj.kakaologin.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kakao_member")
public class KakaoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kakaoIdx;
    private String KakaoEmail;
    private String kakaoNickname;
    private String kakaoProfileImage;

    @Builder
    public KakaoMember(String kakaoEmail, String kakaoNickname, String kakaoProfileImage) {
        this.KakaoEmail = kakaoEmail;
        this.kakaoNickname = kakaoNickname;
        this.kakaoProfileImage = kakaoProfileImage;
    }

}
