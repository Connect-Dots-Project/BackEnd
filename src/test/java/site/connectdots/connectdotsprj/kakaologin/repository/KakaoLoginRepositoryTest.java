package site.connectdots.connectdotsprj.kakaologin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.kakaologin.entity.KakaoMember;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class KakaoLoginRepositoryTest {

    @Autowired
    KakaoLoginRepository kakaoLoginRepository;

//    @Test
//    @DisplayName("카카오로 가입한 사람의 이메일로 조회시, 조회가 성공할 것이다")
//    void findByKakaoEmailTest() {
//        //given
//        String email = "xxxiumin93@gmail.com";
//        //when
//        KakaoMember byKakaoEmail = kakaoLoginRepository.findByKakaoEmail(email);
//        System.out.println("byKakaoEmail = " + byKakaoEmail);
//        //then
//        assertNotNull(byKakaoEmail);
//    }

//    @Test
//    @DisplayName("가입하지 않은 이메일 조회시, null을 반환할 것이다")
//    void findByKakaoEmailTest() {
//        //given
//        String email = "qqwwqdsj2132112@gmail.com";
//        //when
//        KakaoMember byKakaoEmail = kakaoLoginRepository.findByKakaoEmail(email);
//        //then
//        assertNull(byKakaoEmail);
//    }


    @Test
    @DisplayName("카카오 이메일로 카카오멤버 조회를 한다")
    void findByKakaoEmailTest() {
        //given
        String email = "xxxiumin93@gmail.com";
        //when
        Optional<KakaoMember> userOptional = kakaoLoginRepository.findByKakaoEmail(email);
        //then
        assertTrue(userOptional.isPresent());
        KakaoMember kakaoMember = userOptional.get();
        System.out.println("카카오멤버 = " + kakaoMember);
        assertEquals("수민이♡", kakaoMember.getKakaoNickname());
    }

    @Test
    @DisplayName("이메일 중복체크를 하면 중복값이 true여야 한다.")
    void existsByKakaoEmailTrueTest() {
        //given
        String email = "xxxiumin93@gmail.com";
        //when
        boolean flag = kakaoLoginRepository.existsByKakaoEmail(email);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("이메일 중복체크를 하면 중복값이 false여야 한다.")
    void existsByKakaoEmailFalseTest() {
        //given
        String email = "sdajhga234765@gmail.com";
        //when
        boolean flag = kakaoLoginRepository.existsByKakaoEmail(email);
        //then
        assertFalse(flag);
    }


}