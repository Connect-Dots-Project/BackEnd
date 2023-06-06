package site.connectdots.connectdotsprj.musicboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import site.connectdots.connectdotsprj.musicboard.dto.response.SpotifyTokenDTO;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SpotifyAuthService {

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    @Value("${spotify.redirectUri}")
    private String redirectUri;







    private RestTemplate restTemplate;

    @Autowired
    public SpotifyAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public SpotifyTokenDTO getToken(String authorizationCode) {
        SpotifyTokenDTO token = requestAccessToken(authorizationCode);

        if (token != null && token.getRefreshToken() != null) {
            token = refreshAccessToken(token.getRefreshToken());
        }

        return token;
    }

    public String getAuthorizationUrl() {
        String scope = "user-read-private user-read-email"; // 필요한 권한 스코프 설정

        return UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .build().toUriString();
    }


    // 액세스 토큰 요청 로직
    public SpotifyTokenDTO requestAccessToken(String code) {

        String tokenUri = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> request = new HttpEntity<>("grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUri, headers);

        ResponseEntity<SpotifyTokenDTO> response = restTemplate.exchange(tokenUri, HttpMethod.POST, request, SpotifyTokenDTO.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // Handle error case appropriately
            throw new RuntimeException("Spotify API에서 액세스 토큰을 요청하지 못했습니다.");
        }
    }


    // 액세스 토큰 갱신 로직 작성
    public SpotifyTokenDTO refreshAccessToken(String refreshToken) {

        String tokenUri = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> request = new HttpEntity<>("grant_type=refresh_token&refresh_token=" + refreshToken, headers);

        ResponseEntity<SpotifyTokenDTO> response = restTemplate.exchange(tokenUri, HttpMethod.POST, request, SpotifyTokenDTO.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // Handle error case appropriately
            throw new RuntimeException("Spotify API에서 액세스 토큰을 요청하지 못했습니다.");
        }
    }




}




