package site.connectdots.connectdotsprj.kakaologin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.kakaologin.entity.KakaoMember;

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

    @Test
    @DisplayName("가입하지 않은 이메일 조회시, null을 반환할 것이다")
    void findByKakaoEmailTest() {
        //given
        String email = "qqwwqdsj2132112@gmail.com";
        //when
        KakaoMember byKakaoEmail = kakaoLoginRepository.findByKakaoEmail(email);
        //then
        assertNull(byKakaoEmail);
    }


}