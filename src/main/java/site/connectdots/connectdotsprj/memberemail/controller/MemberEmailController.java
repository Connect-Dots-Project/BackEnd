package site.connectdots.connectdotsprj.memberemail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.memberemail.service.MemberEmailService;
import site.connectdots.connectdotsprj.memberemail.dto.MemberEmailCheckRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/connects/sign-up")
@RequiredArgsConstructor
public class MemberEmailController {

    private final MemberEmailService emailService;

    @ResponseBody
    @PostMapping("/mail")        // 이 부분은 각자 바꿔주시면 됩니다.
    public ResponseEntity<String> EmailCheck(@RequestBody MemberEmailCheckRequest emailCheckReq) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailCheckReq.getEmail());
        return ResponseEntity.ok().body/**/(authCode);    // Response body에 값을 반환해줄게요~
    }
}
