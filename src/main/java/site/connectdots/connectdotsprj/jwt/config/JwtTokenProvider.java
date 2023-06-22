package site.connectdots.connectdotsprj.jwt.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.connectdots.connectdotsprj.global.config.TokenUserInfo;
import site.connectdots.connectdotsprj.jwt.entity.Auth;
import site.connectdots.connectdotsprj.jwt.repository.AuthRepository;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final String ACCOUNT = "ACCOUNT";
    private final String LOGIN_METHOD = "LOGIN_METHOD";
    private final String ISSUER = "Connect-dots";
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    private final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private final long ACCESS_TOKEN_VALID_TIME = 2 * 60 * 60 * 1000L;   // TODO: 1분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L; // TODO: 1주

    @Value("${jwt.secret-key}")
    private String SECRET_KEY; // Access Token
    @Value("${jwt.refresh-key}")
    private String REFRESH_KEY; // Refresh Token


    @Transactional
    public boolean isValidRequest(HttpServletRequest request, HttpServletResponse response) {
        // 헤더에 담긴 AccessToken 과 RefreshToken 을 꺼낸다.
        String accessToken = resolveAccessToken(request);
        String refreshToken = resolveRefreshToken(request);

        // Token 검증
        boolean isValidAccessToken = isValidAccessToken(accessToken);
        boolean isValidRefreshToken = isValidRefreshToken(refreshToken);

        if (isValidAccessToken) {
            // access token ok
            return true;
        }


        if (isValidRefreshToken) {
            Claims claimsRefreshToken = getClaimsRefreshToken(refreshToken);
            String account = claimsRefreshToken.get(ACCOUNT, String.class);

            Auth auth = authRepository.findByAccount(account).orElseThrow(() -> new IllegalArgumentException("Refresh Token 이 위조됨"));
            // 유효한 토큰인가
            Member member = memberRepository.findByMemberAccount(account); // 유효한 회원임을 확인

            if (member == null) {
                // 토큰에 있는 account 가 위조됨.
                throw new IllegalArgumentException("Refresh Token 이 위조됨");
            }

            // 유효한 기간인가
            Date expiration = claimsRefreshToken.getExpiration();
            Date now = new Date();

            if (now.after(expiration)) {
                throw new IllegalArgumentException("Refresh Token 이 만료됨");
            }

            // 여기 온 건 유효한 토큰이다.
            // 그래서 새로 access Token 과 Refresh Token 을 발급한다.
            // 발급된 토큰들은 각 위치에 셋팅해준다.
            String newAccessToken = createAccessToken(member);
            response.setHeader(AUTHORIZATION, BEARER + newAccessToken);
            //-----------end Access Token


            //-----------set Refresh Token
            String newRefreshToken = createRefreshToken(member);

            //account 유효
            //새로운 refresh token 을 DB 에 저장한다.
            authRepository.updateRefreshTokenByAccount(newRefreshToken, account);

            // Refresh Token 은 cookie 에 담는다.
            // setSecure 를 해주고
            // setHttpOnly 를 해줘서 보안을 강화시킨다.
            Cookie cookie = new Cookie(REFRESH_TOKEN, newRefreshToken);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);

            int cookieTime = 60 * 60 * 24 * 90;
            cookie.setMaxAge(cookieTime);
            cookie.setPath("/");
            response.addCookie(cookie);
            //-----------end Refresh Token

            return true;
        }

        return false;
    }

    @Transactional
    public void setTokens(HttpServletResponse response, Member member) {
        String account = member.getMemberAccount();
        String newAccessToken = createAccessToken(member);
        response.setHeader(AUTHORIZATION, BEARER + newAccessToken);
        //-----------end Access Token


        //-----------set Refresh Token
        String newRefreshToken = createRefreshToken(member);

        //account 유효
        //새로운 refresh token 을 DB 에 저장한다.
        int i = authRepository.updateRefreshTokenByAccount(newRefreshToken, account);

        // Refresh Token 은 cookie 에 담는다.
        // setSecure 를 해주고
        // setHttpOnly 를 해줘서 보안을 강화시킨다.
        Cookie cookie = new Cookie(REFRESH_TOKEN, newRefreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        int cookieTime = 60 * 60 * 24 * 90;
        cookie.setMaxAge(cookieTime);
        cookie.setPath("/");
        response.addCookie(cookie);
        //-----------end Refresh Token
    }

    /**
     * Access Token 을 발급하는 메서드
     * 유저의 이메일과
     * 로그인 방식을 가지고 있다.
     *
     * @param member : 해당 토큰을 발급 받을 멤버
     * @return
     */
    public String createAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ACCOUNT, member.getMemberAccount());
        claims.put(LOGIN_METHOD, String.valueOf(member.getMemberLoginMethod()));

        Date now = new Date();
        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                )
                .setClaims(claims) // 정보 저장
                .setIssuer(ISSUER) // iss : 발급자 정보
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .setSubject(member.getMemberAccount()) // sub: 토큰을 식별할 수 있는 주요 데이터
                .compact();
    }


    /**
     * Refresh Token 을 발급해주는 메서드
     * 유저의 이메일을 가지고 있다.
     *
     * @param member : 해당 토큰을 발급 받을 멤버
     * @return
     */
    public String createRefreshToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ACCOUNT, member.getMemberAccount());

        Date now = new Date();

        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(REFRESH_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                )
                .setClaims(claims) // 정보 저장
                .setIssuer(ISSUER) // iss : 발급자 정보
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME)) // set Expire Time
                .setSubject(member.getMemberAccount()) // sub: 토큰을 식별할 수 있는 주요 데이터
                .compact();
    }


    public TokenUserInfo validateAndGetTokenUserInfo(String token) {
        System.out.println("------------- token" + token);

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
                .account(claims.get(ACCOUNT, String.class))
                .memberLoginMethod(claims.get(LOGIN_METHOD, String.class))
                .build();
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(authorization)
                && authorization.startsWith(BEARER)) {
            return authorization.substring(7);
        }

        return null;
    }

    private String resolveRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * SECRET_KEY 복호화
     *
     * @param accessToken : 복호화 대상
     * @return
     */
    public Claims getClaimsAccessToken(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /**
     * REFRESH_KEY 복호화
     *
     * @param refreshToken : 복호화 대상
     * @return
     */
    public Claims getClaimsRefreshToken(String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(REFRESH_KEY.getBytes()))
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    /**
     * AccessToken 을 검증하는 메서드
     *
     * @param accessToken
     * @return
     */
    public boolean isValidAccessToken(String accessToken) {
        System.out.println("isValidToken is : " + accessToken);

        if (accessToken == null) return false;

        try {
            Claims accessClaims = getClaimsAccessToken(accessToken);

            System.out.println(accessClaims);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get(ACCOUNT));

            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get(ACCOUNT));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    /**
     * RefreshToken 을 검증하는 메서드
     *
     * @param refreshToken
     * @return
     */
    public boolean isValidRefreshToken(String refreshToken) {
        System.out.println("isValidToken is : " + refreshToken);

        try {
            if (refreshToken == null) return false;

            Claims accessClaims = getClaimsRefreshToken(refreshToken);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get(ACCOUNT));

            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get(ACCOUNT));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }


}
