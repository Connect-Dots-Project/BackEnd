package site.connectdots.connectdotsprj.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import site.connectdots.connectdotsprj.global.token.filter.JwtAuthFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .antMatchers("/**")
//                .permitAll();
//
//        return http.build();
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                // 세션 인증을 사용하지 않겠다
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 어떤 요청에서 인증을 안 할 것인지 설정, 언제 할 것인지 설정
                .authorizeRequests()
//                .antMatchers(HttpMethod.PUT, "/contents/hot-place").authenticated()
//                .antMatchers(HttpMethod.POST, "/contents/hot-place").authenticated()
//                .antMatchers(HttpMethod.PATCH, "/contents/hot-place").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/hot-place/{location}").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/hot-place/img/{fileName}").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/contents/hot-place/{hotplaceIdx}").authenticated()
//                .antMatchers(HttpMethod.POST, "/contents/free-board").authenticated()
//                .antMatchers(HttpMethod.PATCH, "/contents/free-board").authenticated()
//                .antMatchers(HttpMethod.POST, "/contents/free-board/replies").authenticated()
//                .antMatchers(HttpMethod.POST, "/contents/free-board/like/{freeBoardIdx}").authenticated()
//                .antMatchers(HttpMethod.POST, "/contents/free-board/hate/{freeBoardIdx}").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/free-board/my-page/free-board").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/free-board/detail/{freeBoardIdx}").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/contents/free-board/{freeBoardIdx}").authenticated()
//                .antMatchers(HttpMethod.POST, "/contents/chat").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/chat/hashtag").authenticated()
//                .antMatchers(HttpMethod.POST, "/chat/room").authenticated()
//                .antMatchers(HttpMethod.GET, "/chat/room/{roomId}").authenticated()
//                .antMatchers(HttpMethod.GET, "/member/mypage/{memberIdx}").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/music-board").authenticated()
//                .antMatchers(HttpMethod.GET, "/contents/csv").authenticated()
//                .anyRequest().permitAll()

                .antMatchers("/**").permitAll() // 이 요청은 허용
//                .antMatchers("/","/myapi.html","/img/{fileName}").permitAll()
//                .antMatchers(HttpMethod.GET, "/contents/hot-place", "/contents/free-board/{page}", "/contents/chat", "/chat/rooms").permitAll()
//                .antMatchers("/connects/sign-up","/connects/login", "/connects/sign-up/email", "/connects/sign-up/check", "/sns/naver", "/naver/login", "/sns/kakao", "/kakao/login").permitAll()
//                .anyRequest().authenticated() // 그 외 나머지 요청은 인증필요
        ;

        // 토큰 인증 필터 연결
        http.addFilterAfter(
                jwtAuthFilter
                , CorsFilter.class // import 주의: 스프링꺼
        );

        return http.build();
    }
}
