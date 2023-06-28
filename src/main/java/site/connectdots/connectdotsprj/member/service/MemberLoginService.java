package site.connectdots.connectdotsprj.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import site.connectdots.connectdotsprj.jwt.config.JwtTokenProvider;
import site.connectdots.connectdotsprj.member.dto.request.MemberLoginRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberPhoneRequestDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberLoginResponseDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberPhoneResponseDTO;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.exception.custom.LoginFailException;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static site.connectdots.connectdotsprj.member.exception.custom.enums.LoginFailErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider tokenProvider;

    public MemberLoginResponseDTO login(MemberLoginRequestDTO dto, HttpServletResponse response) {
        Member foundMember = memberRepository.findByMemberAccount(dto.getAccount());

        if (foundMember == null) {
            throw new LoginFailException(LOGIN_FAIL_EXCEPTION);
        }

        if (!encoder.matches(dto.getPassword(), foundMember.getMemberPassword())) {
            throw new LoginFailException(LOGIN_FAIL_EXCEPTION);
        }

        Member saved = memberRepository.save(foundMember);
        tokenProvider.setTokens(response, foundMember);

        return new MemberLoginResponseDTO(saved);
    }

    public MemberPhoneResponseDTO findAccountByPhone(MemberPhoneRequestDTO dto) {
        Member byMemberPhone = memberRepository.findByMemberPhone(dto.getPhone());
        if (byMemberPhone == null) {
            return MemberPhoneResponseDTO.builder()
                    .account(null)
                    .build();
        }

        return MemberPhoneResponseDTO.builder()
                .account(byMemberPhone.getMemberAccount())
                .build();
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("REFRESH_TOKEN", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }
}
