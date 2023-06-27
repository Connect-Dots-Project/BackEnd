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
        registry
                .addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**") //
//                .addPathPatterns("/*") // /help  /help/helpe
                .excludePathPatterns("/connects/sign-up")
                .excludePathPatterns("/connects/sign-up/email")
                .excludePathPatterns("/connects/sign-up/check")
                .excludePathPatterns("/connects/login")
//                .excludePathPatterns("/**")
        ;
    }
}
