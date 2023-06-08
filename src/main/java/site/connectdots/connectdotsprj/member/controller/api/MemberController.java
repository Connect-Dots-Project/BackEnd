package site.connectdots.connectdotsprj.member.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.service.MemberSignUpService;

@RestController
@RequestMapping("/connects")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSignUpService memberService;

    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody MemberSignUpRequestDTO dto) {
        return memberService.signUp(dto);
    }


}
