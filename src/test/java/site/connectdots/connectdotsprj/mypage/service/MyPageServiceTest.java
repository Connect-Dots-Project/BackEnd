package site.connectdots.connectdotsprj.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyPageServiceTest {
@Autowired
    private MyPageService myPageService;

@Test
    @DisplayName("50명의 회원을 생성되어야한다")
    void membertest(){

}
}