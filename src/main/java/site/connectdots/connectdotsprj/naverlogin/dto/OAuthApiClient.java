package site.connectdots.connectdotsprj.naverlogin.dto;

import site.connectdots.connectdotsprj.naverlogin.dto.response.OAuthInfoResponse;
import site.connectdots.connectdotsprj.naverlogin.entity.OAuthLoginParams;
import site.connectdots.connectdotsprj.naverlogin.entity.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}
