package site.connectdots.connectdotsprj.kakaologin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import site.connectdots.connectdotsprj.kakaologin.dto.KakaoLoginRequestDTO;

import java.util.Map;

@Service
@Slf4j
public class KakaoLoginService {

    public void kakaoService(Map<String, String> requestMap) {
        String accessToken = getKakaoAccessToken(requestMap);
        log.info("발급받은 토큰 : {}", accessToken);

    }




    private String getKakaoAccessToken(Map<String, String> requestMap) {

        String requestUri = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestMap.get("appkey"));
        params.add("redirect_uri", requestMap.get("redirect"));
        params.add("code", requestMap.get("code"));


        RestTemplate template = new RestTemplate();

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        Map<String, Object> responseData = (Map<String, Object>) responseEntity.getBody();
        log.info("토큰요청 응답데이터 : {}", responseData);

        return (String) responseData.get("access_token");

    }

}
