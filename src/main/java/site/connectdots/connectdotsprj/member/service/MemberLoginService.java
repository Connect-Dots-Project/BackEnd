package site.connectdots.connectdotsprj.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.member.dto.request.MemberLoginRequestDTO;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.exception.custom.LoginFailException;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static site.connectdots.connectdotsprj.member.exception.custom.enums.LoginFailErrorCode.*;
import static site.connectdots.connectdotsprj.member.util.LoginUtil.*;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final MemberRepository memberRepository;

    public boolean login(MemberLoginRequestDTO dto, HttpSession session, HttpServletResponse response) {
        System.out.println(dto);
        System.out.println(session);
        System.out.println(response);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        Member foundMember = memberRepository.findByMemberAccount(dto.getAccount());
        if (foundMember == null || !foundMember.getMemberPassword().equals(dto.getPassword())) {
            throw new LoginFailException(LOGIN_FAIL_EXCEPTION);
        }

        if (dto.getIsAutoLogin()) {
            setAutoLogin(session, response, foundMember);
        }

        maintainLoginState(session, foundMember);

        foundMember.setMemberSessionId(session.getId());
        memberRepository.save(foundMember);

        return true;
    }

    public void maintainLoginState(HttpSession session, Member member) {
        session.setAttribute(LOGIN_KEY, member);
        session.setMaxInactiveInterval(SESSION_DATE);
    }

    private void setAutoLogin(HttpSession session, HttpServletResponse response, Member foundMember) {
        Cookie cookie = setCookie(session);
        response.addCookie(cookie);

        foundMember.setMemberCookieDate(LocalDateTime.now().plusDays(COOKIE_DAY));
        memberRepository.save(foundMember);
    }

    private Cookie setCookie(HttpSession session) {
        Cookie cookie = new Cookie(AUTO_LOGIN, session.getId());

        cookie.setMaxAge(COOKIE_DATE);
        cookie.setPath(COOKIE_PATH);

        return cookie;
    }

}
