package site.connectdots.connectdotsprj.kakaologin.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.kakaologin.dto.KakaoLoginRequestDTO;
import site.connectdots.connectdotsprj.kakaologin.service.KakaoLoginService;

import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${sns.kakao.app-key}")
    private String kakaoAppKey;

    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRedirectURI;

    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        String requestUri = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", kakaoAppKey, kakaoRedirectURI);
        return "redirect:" + requestUri;
    }

    @GetMapping("/sns/kakao")
    public String snsKakao(String code) {
        log.info("인가코드 : {}", code);

        HashMap<String, String> map = new HashMap<>();
        map.put("appkey", kakaoAppKey);
        map.put("redirect", kakaoRedirectURI);
        map.put("code", code);
        kakaoLoginService.kakaoService(map);

        return "";
    }

}
