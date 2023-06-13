package site.connectdots.connectdotsprj.global.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String createToken(Member member) {

        Date expiry = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getMemberAccount());

        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                )
                .setIssuer("ConnectsDots")
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .setSubject(member.getMemberAccount())
                .setClaims(claims)
                .compact();
    }
}
