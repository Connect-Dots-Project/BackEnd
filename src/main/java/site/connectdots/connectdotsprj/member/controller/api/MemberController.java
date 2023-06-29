package site.connectdots.connectdotsprj.member.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.member.dto.request.*;
import site.connectdots.connectdotsprj.member.dto.response.*;
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

    @PostMapping("/sign-up/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody MemberNicknameCheckRequestDTO dto) {
        MemberNicknameCheckResponseDTO response = memberSignUpService.checkNickname(dto);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/sign-up/check-phone")
    public ResponseEntity<MemberPhoneCheckResponseDTO> checkPhone(@RequestBody MemberPhoneRequestDTO dto) {
        MemberPhoneCheckResponseDTO responseDTO = memberSignUpService.checkPhone(dto);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/sign-up/check-email")
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


    @PostMapping("/login/find/account")
    public ResponseEntity<?> findAccount(@RequestBody MemberPhoneRequestDTO dto) {
        // 아이디 찾기
        MemberPhoneResponseDTO response = memberLoginService.findAccountByPhone(dto);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login/find/password")
    public void test1() {
        // 비밀번호 찾기

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        memberLoginService.logout(response);
        return ResponseEntity.ok().body("ok");
    }


}