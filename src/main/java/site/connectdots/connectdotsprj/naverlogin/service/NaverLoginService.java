package site.connectdots.connectdotsprj.naverlogin.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class NaverLoginService {


    public void getProfile(String accessToken, String refreshToken, String tokenType, String expiresIn) {
        String requestURI = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate template = new RestTemplate();
        ResponseEntity<Object> responseEntity = template.exchange(requestURI, HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println(responseEntity);
        System.out.println("----------------------------");
        System.out.println("----------------------------");

        //profile_image=https://ssl.pstatic.net/static/pwe/address/img_profile.png,
        // gender=M,
        // mobile=010-3779-0201,
        // mobile_e164=+821037790201,
        // name=이기덕,
        // birthday=05-11,
        // birthyear=1992


    }
}
