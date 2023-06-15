package site.connectdots.connectdotsprj.naverlogin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import site.connectdots.connectdotsprj.naverlogin.service.NaverLoginService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class NaverLoginController {

    private final NaverLoginService naverLoginService;

    @Value("${sns.naver.app-key}")
    private String naverAppKey;

    @Value("${sns.naver.app-secret}")
    private String naverSecret;

    @Value("${sns.naver.redirect-uri}")
    private String naverRedirectURI;

    @GetMapping("/naver/login")
    public ResponseEntity<?> naverLogin() {
        String requestURI = String.format("https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s", naverAppKey, naverRedirectURI);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", requestURI);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/sns/naver")
    public ResponseEntity<?> snsNaver(String code) {
        System.out.println(code);
//        String tokenRequestURI = String.format("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s", naverAppKey, naverSecret, code);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", tokenRequestURI);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(tokenRequestURI, String.class);
//        // response.getBody()를 통해 JSON 응답을 얻을 수 있습니다.
//        String responseBody = response.getBody();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, String> responseMap = null;
//
//        try {
//            // JSON 문자열을 Map 형태로 파싱합니다.
//            responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, String>>() {
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // responseMap에서 필요한 키-값을 추출하여 활용합니다.
//        if (responseMap != null) {
//            String accessToken = responseMap.get("access_token");
//            String refreshToken = responseMap.get("refresh_token");
//            String tokenType = responseMap.get("token_type");
//            String expiresIn = responseMap.get("expires_in");
//
//            System.out.println("Access Token: " + accessToken);
//            System.out.println("Refresh Token: " + refreshToken);
//            System.out.println("Token Type: " + tokenType);
//            System.out.println("Expires In: " + expiresIn);
//
//            // 필요한 처리를 수행하고 응답을 반환합니다.
//
//            naverLoginService.getProfile(accessToken,refreshToken,tokenType,expiresIn);
//        }
//
//
//
//        return new ResponseEntity<>(headers, HttpStatus.FOUND);
        return null;
    }


}
