package site.connectdots.connectdotsprj.member.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("전체 조회에 성공할 것이며 사이즈는 50이다")
    void findAllTest() {
        //given
        //when
        List<Member> memberList = memberRepository.findAll();
        memberList.forEach(System.out::println);

        //then
        Assertions.assertEquals(50, memberList.size());
    }

}