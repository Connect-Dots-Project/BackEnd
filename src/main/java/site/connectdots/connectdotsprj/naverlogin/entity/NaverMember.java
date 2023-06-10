package site.connectdots.connectdotsprj.naverlogin.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class NaverMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long naverId;
    private String email;
    private String nickname;
    private OAuthProvider oAuthProvider;

    @Builder
    public NaverMember(String email, String nickname, OAuthProvider oAuthProvider) {
        this.email = email;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
    }


}
