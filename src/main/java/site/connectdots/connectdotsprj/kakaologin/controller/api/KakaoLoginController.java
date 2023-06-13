package site.connectdots.connectdotsprj.kakaologin.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.kakaologin.dto.KakaoLoginRequestDTO;
import site.connectdots.connectdotsprj.kakaologin.service.KakaoLoginService;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${sns.kakao.app-key}")
    private String kakaoAppKey;

    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRedirectURI;

    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin() {
        String requestUri = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", kakaoAppKey, kakaoRedirectURI);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", requestUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/sns/kakao")
    public ResponseEntity<?> snsKakao(String code) {
        log.info("인가코드 : {}", code);

        HashMap<String, String> map = new HashMap<>();
        map.put("appkey", kakaoAppKey);
        map.put("redirect", kakaoRedirectURI);
        map.put("code", code);
        try {
            kakaoLoginService.kakaoService(map);
            return ResponseEntity.ok().body(map);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }



}
