//package site.connectdots.connectdotsprj.jwt.filter;
//
//
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import site.connectdots.connectdotsprj.jwt.config.JwtTokenProvider;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    // 필터가 해야할 작업을 기술
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException {
//
//
////        try {
//
//
//
////            if (!jwtTokenProvider.isValidAccessToken(accessToken)) {
////                System.out.println("토큰이 유효하지 않음.");
////            }
////
////
////            System.out.println("\n\n\n\n\n\n\n\n\n\n---------------back up-----------------");
////
////
////            if (accessToken != null) {
////
////                // 토큰 서명 위조 검사와 토큰을 파싱해서 클레임을 얻어내는 작업
//////                TokenUserInfo userInfo = jwtTokenProvider.validateAndGetTokenUserInfo(token);
////
////
////                // 인가 정보 리스트
////                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//////                authorityList.add(new SimpleGrantedAuthority(userInfo.getRole().toString()));
//////                authorityList.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getMemberLoginMethod()));
////
////
////                // 인증 완료 처리
////                // - 스프링 시큐리티에게 인증 정보를 전달해서 전역적으로 앱에서
////                //   인증 정보를 활용할 수 있게 설정
////                AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
////                        null, // 컨트롤러에서 활용할 유저 정보
////                        null, // 인증된 사용자의 비밀번호 - 보통 널값
////                        authorityList // 인가 정보 (권한 정보)
////                );
////
////                // 인증 완료 처리시 클라이언트의 요청 정보 세팅
////                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////
////                // 스프링 시큐리티 컨테이너에 인증 정보 객체 등록
////                //.antMatchers("/jwt/test/get-token").permitAll()
////                SecurityContextHolder.getContext().setAuthentication(auth);
//
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            log.error("토큰이 위조 되었습니다. by JwtAuthFilter");
////        }
//
//        // 필터 체인에 내가 만든 필터 실행 명령
////        filterChain.doFilter(request, response);
//
//    }
//
//}