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
    @DisplayName("insert bulk")
    @Rollback()
    void insertBulk() {

        for (int i = 1; i <= 50; i++) {
            int number1 = (int) (Math.random() * 9000) + 1000;
            int number2 = (int) (Math.random() * 9000) + 1000;

            Gender gender = Gender.M;
            if (i % 2 == 0) {
                gender = Gender.F;
            }

            LocalDateTime startDate = LocalDateTime.of(1983, 1, 1, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(2003, 12, 31, 0, 0);

            long days = startDate.until(endDate, ChronoUnit.DAYS);
            long randomDays = (long) (Math.random() * days);

            LocalDateTime randomDate = startDate.plusDays(randomDays);

            memberRepository.save(
                    Member.builder()
                            .memberAccount("account" + i + "@google.com")
                            .memberPassword("password" + i)
                            .memberName("name" + i)
                            .memberNickname("nickName" + i)
                            .memberGender(gender)
                            .memberBirth(randomDate)
                            .memberPhone("010-" + number1 + "-" + number2)
                            .memberLocation("강남구")
                            .memberComment("hello world" + i)
                            .build()
            );

        }

    }

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