package site.connectdots.connectdotsprj.global.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.entity.MemberLoginMethod;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String createToken(Member memberEntity) {

        // 토큰 만료시간 생성
        Date expiry = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        // 토큰 생성
        /**
         *  {
         *      "iss" : "딸긔공듀",
         *      "exp" : "2023-07-12",
         *      "iat" : "2023-06-12",
         *      "email" : "로그인한 사람 이메일",
         *      "role" : "Premium"
         *
         *      == 서명
         *  }
         */

        // 추가 클레임 정의
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", memberEntity.getMemberAccount());
        claims.put("loginMethod", String.valueOf(memberEntity.getMemberLoginMethod()));
//        claims.put("role", memberEntity.getRole().toString());

        return Jwts.builder()
                // token header에 들어갈 서명
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                )
                // token payload 에 들어갈 클레임 설정
                .setClaims(claims) // 커스텀 클레임은 맨 위에 있어야 삭제가 안 된다
                .setIssuer("Connect-dots") // iss : 발급자 정보
                .setIssuedAt(new Date()) // iat: 발급시간
                .setExpiration(expiry) // exp: 만료시간
                .setSubject(memberEntity.getMemberAccount()) // sub: 토큰을 식별할 수 있는 주요 데이터
                .compact();
    }

    public TokenUserInfo validateAndGetTokenUserInfo(String token) {

        Claims claims = Jwts.parserBuilder()
                // 토큰 발급자의 발급 당시의 서명을 넣어 줌
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                // 서명 위조 검사: 위조된 경우 예외가 발생
                // 위조가 되지 않은 경우 페이로드를 리턴
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("claims: {}", claims);

        return TokenUserInfo.builder()
                .account(claims.get("account", String.class))
                .memberLoginMethod(claims.get("loginMethod", String.class))
                .build();
    }
}
