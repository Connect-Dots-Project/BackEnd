package site.connectdots.connectdotsprj.naverlogin.dto.response;

import site.connectdots.connectdotsprj.naverlogin.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();

    String getNickname();

    OAuthProvider getOAuthProvider();
}
