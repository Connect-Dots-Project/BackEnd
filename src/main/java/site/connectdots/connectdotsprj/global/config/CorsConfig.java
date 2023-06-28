package site.connectdots.connectdotsprj.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 전역 크로스 오리진 설정
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // 어떤 요청에 대해서 허용할지?
                .allowedOrigins("http://localhost:3000", "http://330-special-bucket.s3-website.ap-northeast-2.amazonaws.com") // 어떤 클라이언트들을 허용할지? , 여러개 가능
                .allowedOrigins("http://connect-dots.site", "http://330-special-bucket.s3-website.ap-northeast-2.amazonaws.com") // 어떤 클라이언트들을 허용할지? , 여러개 가능
                .allowedMethods("*") // 어떤 메소드들을 허용할지?
                .allowedHeaders("*") // 어떤 요청 헤더를 허용할지?
                .allowCredentials(true) // 쿠키 전달을 허용할 것인가?
                .maxAge(3600) // 캐싱 시간을 설정
                .exposedHeaders("Authorization", "Set-Cookie") // 헤더 셋팅 설정 권한
        ;

    }

}
