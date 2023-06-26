package site.connectdots.connectdotsprj.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.member.dto.request.MemberSignUpRequestDTO;
import site.connectdots.connectdotsprj.member.dto.response.MemberSignUpResponseDTO;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.MemberLoginMethod;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberSignUpServiceTest {

    @Autowired
    MemberSignUpService memberSignUpService;

    @Test
    @DisplayName("회원가입 성공시 암호화된 비밀번호로 저장이 될 것이다.")
    void signUpTest() {
        //given
        MemberSignUpRequestDTO dto = MemberSignUpRequestDTO.builder()
                .account("hoho1@naver.com")
                .firstPassword("qwer1234!")
                .secondPassword("qwer1234!")
                .name("홍길동")
                .nickName("디비버")
                .birthDay(LocalDate.now())
                .gender(Gender.M)
                .phone("010-0491-4819")
                .location("강남구")
                .comment("Junit 으로 가입함.")
                .loginMethod(MemberLoginMethod.COMMON)
                .build();

        MemberSignUpResponseDTO memberSignUpResponseDTO = memberSignUpService.signUp(dto);

        System.out.println(memberSignUpResponseDTO);
        //when

        //then
    }

}