package site.connectdots.connectdotsprj.global.token.filter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import site.connectdots.connectdotsprj.global.config.TokenProvider;
import site.connectdots.connectdotsprj.global.config.TokenUserInfo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    // 필터가 해야할 작업을 기술
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);
            log.info("Jwt Token Filter is running... - token : {}", token);

//             토큰 위조 검사 및 인증 완료 처리
            if (token != null) {

                // 토큰 서명 위조 검사와 토큰을 파싱해서 클레임을 얻어내는 작업
                TokenUserInfo userInfo = tokenProvider.validateAndGetTokenUserInfo(token);

                // 인가 정보 리스트
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//                authorityList.add(new SimpleGrantedAuthority(userInfo.getRole().toString()));
                authorityList.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getMemberLoginMethod()));

                // 인증 완료 처리
                // - 스프링 시큐리티에게 인증 정보를 전달해서 전역적으로 앱에서
                //   인증 정보를 활용할 수 있게 설정
                AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userInfo, // 컨트롤러에서 활용할 유저 정보
                        null, // 인증된 사용자의 비밀번호 - 보통 널값
                        authorityList // 인가 정보 (권한 정보)
                );

                // 인증 완료 처리시 클라이언트의 요청 정보 세팅
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 스프링 시큐리티 컨테이너에 인증 정보 객체 등록
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("토큰이 위조되었습니다.");
        }

        // 필터 체인에 내가 만든 필터 실행 명령
        filterChain.doFilter(request, response);

    }

    private String parseBearerToken(HttpServletRequest request) {
        // 요청 헤더에서 토큰 가져오기
        // http request header
        // -- Content-type : application/json
        // -- Authorization : Bearer ajskdflajsdlkfadsfjkl
        String bearerToken = request.getHeader("Authorization");

        // 요청 헤더에서 가져온 토큰은 순수 토큰 값이 아니라
        // 앞에 Bearer 가 붙어 있으니 이것을 제거하는 작업
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}