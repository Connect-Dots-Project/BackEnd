package site.connectdots.connectdotsprj.naverlogin.service;

import org.springframework.stereotype.Component;
import site.connectdots.connectdotsprj.naverlogin.dto.OAuthApiClient;
import site.connectdots.connectdotsprj.naverlogin.dto.response.OAuthInfoResponse;
import site.connectdots.connectdotsprj.naverlogin.entity.OAuthLoginParams;
import site.connectdots.connectdotsprj.naverlogin.entity.OAuthProvider;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oauthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }
}
