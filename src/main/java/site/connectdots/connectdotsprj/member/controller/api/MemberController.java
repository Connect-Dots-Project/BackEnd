package site.connectdots.connectdotsprj.member.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.member.dto.request.MemberLoginRequestDTO;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberLoginResponseDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberSignUpResponseDTO;
import site.connectdots.connectdotsprj.member.service.MemberLoginService;
import site.connectdots.connectdotsprj.member.service.MemberSignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDTO> login(@RequestBody MemberLoginRequestDTO dto,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        MemberLoginResponseDTO loginResponse = memberLoginService.login(dto, request.getSession(), response);

        return ResponseEntity.ok().body(loginResponse);
    }

}