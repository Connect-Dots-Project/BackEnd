package site.connectdots.connectdotsprj.naverlogin.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.naverlogin.entity.NaverLoginParams;
import site.connectdots.connectdotsprj.naverlogin.service.NaverLoginService;
import site.connectdots.connectdotsprj.util.jwt.AuthTokens;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class NaverLoginController {
//    private final NaverLoginService oAuthLoginService;
//
//    @PostMapping("/naver")
//    public ResponseEntity<AuthTokens> loginNaver(@RequestBody NaverLoginParams params) {
//        return ResponseEntity.ok(oAuthLoginService.login(params));
//    }
}
