package site.connectdots.connectdotsprj.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.jwt.config.JwtTokenProvider;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.entity.MemberLoginMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/jwt/test")
@RequiredArgsConstructor
public class JwtTokenTestController {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 토큰을 얻는 메서드
     *
     * @return
     */
    @GetMapping("/get-token")// Login 성공하면
    public ResponseEntity<?> getToken(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("------------------getToken----------------");
        // 로그인을 위한 어떤 DTO
        // 그 DTO 를 받아서 .getAccount();
        // test1@naver.com

        Member member = Member.builder()
                .memberAccount("test1@naver.com")
                .memberLoginMethod(MemberLoginMethod.COMMON)
                .build();

        System.out.println("---------------setTokens---------------");
        jwtTokenProvider.setTokens(response, member);
        System.out.println("---------------setTokens---------------");

        return ResponseEntity.ok().body("발급 완료");
    }


    @GetMapping("/auth")
    public ResponseEntity<?> checkToken(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("---------✅-----------checkToken-----------------");

        return ResponseEntity.ok().body("통과되었습니다.");
    }

}
