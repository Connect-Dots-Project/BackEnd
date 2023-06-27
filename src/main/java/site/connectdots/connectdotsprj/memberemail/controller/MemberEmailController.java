package site.connectdots.connectdotsprj.memberemail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.memberemail.dto.request.MemberCodeResponseDTO;
import site.connectdots.connectdotsprj.memberemail.dto.response.MemberCodeCheckResponseDTO;
import site.connectdots.connectdotsprj.memberemail.service.MemberEmailService;
import site.connectdots.connectdotsprj.memberemail.dto.MemberEmailCheckRequestDTO;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/connects/sign-up")
@RequiredArgsConstructor
public class MemberEmailController {

    private final MemberEmailService emailService;

    @PostMapping("/email")        // 이 부분은 각자 바꿔주시면 됩니다.
    public ResponseEntity<MemberCodeResponseDTO> EmailCheck(@Valid @RequestBody MemberEmailCheckRequestDTO emailCheckReq) throws MessagingException, UnsupportedEncodingException {
        MemberCodeResponseDTO memberCodeResponseDTO = emailService.sendEmail(emailCheckReq.getEmail());

        return ResponseEntity.ok().body/**/(memberCodeResponseDTO);    // Response body에 값을 반환해줄게요~
    }

    @PostMapping("/check")
    public ResponseEntity<MemberCodeCheckResponseDTO> codeCheck(@RequestBody MemberCodeResponseDTO dto) {
        MemberCodeCheckResponseDTO memberCodeCheckResponseDTO = emailService.checkCode(dto.getCode());

        return ResponseEntity.ok().body(memberCodeCheckResponseDTO);
    }

}
