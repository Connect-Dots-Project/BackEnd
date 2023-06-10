package site.connectdots.connectdotsprj.naverlogin.entity;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oauthProvider();
    MultiValueMap<String, String> makeBody();
}
