package site.connectdots.connectdotsprj.member.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.member.dto.request.MemberLoginRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberLogoutRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.service.MemberLoginService;
import site.connectdots.connectdotsprj.member.service.MemberLogoutService;
import site.connectdots.connectdotsprj.member.service.MemberSignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/connects")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSignUpService memberSignUpService;
    private final MemberLoginService memberLoginService;
    private final MemberLogoutService memberLogoutService;

    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody MemberSignUpRequestDTO dto) {
        return memberSignUpService.signUp(dto);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody MemberLoginRequestDTO dto,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        return memberLoginService.login(dto, request.getSession(), response);
    }

    @PostMapping("/logout")
    public boolean logout(@RequestBody MemberLogoutRequestDTO dto) {
        return memberLogoutService.logout(dto.getEmail());
    }

}