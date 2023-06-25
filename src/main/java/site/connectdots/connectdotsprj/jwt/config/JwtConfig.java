package site.connectdots.connectdotsprj.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.connectdots.connectdotsprj.jwt.interceptor.JwtTokenInterceptor;

@Configuration
@RequiredArgsConstructor
public class JwtConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO : URL 설정해야 함
        registry
                .addInterceptor(jwtTokenInterceptor)
//                .addPathPatterns("/**")
                .excludePathPatterns("/**") // TODO : 메인페이지는 서버에서 알 수 없다. 이 경로가 맞나?
//                .excludePathPatterns("/jwt/test/get-token")
//                .excludePathPatterns("/connects/sign-up")
//                .excludePathPatterns("/contents/free-board/**")
//                .excludePathPatterns("/connects/login")
        ;
    }
}
