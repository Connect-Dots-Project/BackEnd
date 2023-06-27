package site.connectdots.connectdotsprj.member.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.member.dto.request.MemberEmailCheckRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberLoginRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberNicknameCheckRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberEmailCheckResponseDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberLoginResponseDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberNicknameCheckResponseDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberSignUpResponseDTO;
import site.connectdots.connectdotsprj.member.service.MemberLoginService;
import site.connectdots.connectdotsprj.member.service.MemberSignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/connects")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSignUpService memberSignUpService;
    private final MemberLoginService memberLoginService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberSignUpResponseDTO> signUp(@RequestBody MemberSignUpRequestDTO dto) {
        MemberSignUpResponseDTO memberSignUpResponseDTO = memberSignUpService.signUp(dto);

        return ResponseEntity.ok().body(memberSignUpResponseDTO);
    }

    @PostMapping("sign-up/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody MemberNicknameCheckRequestDTO dto) {
        MemberNicknameCheckResponseDTO response = memberSignUpService.checkNickname(dto);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("sign-up/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody @Valid MemberEmailCheckRequestDTO dto) {
        MemberEmailCheckResponseDTO response = memberSignUpService.checkEmail(dto);

        System.out.println(response);

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDTO> login(@RequestBody MemberLoginRequestDTO dto
            , HttpServletResponse response
    ) {
        MemberLoginResponseDTO loginResponse = memberLoginService.login(dto, response);

        return ResponseEntity.ok().body(loginResponse);
    }

}