package site.connectdots.connectdotsprj.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import site.connectdots.connectdotsprj.jwt.config.JwtTokenProvider;
import site.connectdots.connectdotsprj.member.dto.request.MemberLoginRequestDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberLoginResponseDTO;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.exception.custom.LoginFailException;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static site.connectdots.connectdotsprj.member.exception.custom.enums.LoginFailErrorCode.*;
import static site.connectdots.connectdotsprj.member.util.LoginUtil.*;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

    public MemberLoginResponseDTO login(MemberLoginRequestDTO dto
            , HttpServletResponse response
            , HttpServletRequest request

    ) {
        Member foundMember = memberRepository.findByMemberAccount(dto.getAccount());
        if (foundMember == null || !foundMember.getMemberPassword().equals(dto.getPassword())) {
            throw new LoginFailException(LOGIN_FAIL_EXCEPTION);
        }
        // 비밀번호 확인해야함.

//        if (dto.getIsAutoLogin()) {
//            setAutoLogin(session, response, foundMember);
//        }
//
//        maintainLoginState(session, foundMember);

        memberRepository.save(foundMember);

        tokenProvider.setTokens(response, foundMember);

        return new MemberLoginResponseDTO(foundMember);
    }

    public void maintainLoginState(HttpSession session, Member member) {
        session.setAttribute(LOGIN_KEY, member);
        session.setMaxInactiveInterval(SESSION_DATE);
    }

    private void setAutoLogin(HttpSession session, HttpServletResponse response, Member foundMember) {
        Cookie cookie = setCookie(session);
        response.addCookie(cookie);

        memberRepository.save(foundMember);
    }

    private Cookie setCookie(HttpSession session) {
        Cookie cookie = new Cookie(AUTO_LOGIN, session.getId());

        cookie.setMaxAge(COOKIE_DATE);
        cookie.setPath(COOKIE_PATH);

        return cookie;
    }

}
