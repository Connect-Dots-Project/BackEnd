package site.connectdots.connectdotsprj.naverlogin.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.naverlogin.service.NaverLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class NaverLoginController {
    private final NaverLoginService loginService;

    @GetMapping("/naver-login")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        String url = loginService.getNaverAuthorizeUrl("authorize");
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
