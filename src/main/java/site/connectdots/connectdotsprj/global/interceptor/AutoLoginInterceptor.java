package site.connectdots.connectdotsprj.global.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;
import site.connectdots.connectdotsprj.member.service.MemberLoginService;
import site.connectdots.connectdotsprj.member.util.LoginUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final MemberLoginService memberLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookie = WebUtils.getCookie(request, LoginUtil.AUTO_LOGIN);

        if (cookie != null) {
            String sessionId = cookie.getValue();
            Member foundMember = memberRepository.findByMemberSessionId(sessionId);

            if (foundMember != null && LocalDateTime.now().isBefore(foundMember.getMemberCookieDate())) {
                memberLoginService.maintainLoginState(request.getSession(), foundMember);
            }
        }

        return true;
    }

}
