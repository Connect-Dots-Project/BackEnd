package site.connectdots.connectdotsprj.common;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class InsertBulk {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FreeBoardRepository freeBoardRepository;


    @Autowired
    private FreeBoardReplyRepository freeBoardReplyRepository;

    @Test
    @DisplayName("insert bulk")
    void insertBulk() {

        for (int i = 1; i <= 100; i++) {
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

            String[] location = {"도봉구", "노원구", "강북구", "은평구", "종로구", "성북구", "중랑구", "서대문구", "동대문구",
                    "강서구", "마포구", "중구", "성동구", "광진구", "강동구", "영등포구", "용산구", "양천구",
                    "구로구", "동작구", "송파구", "강남구", "서초구", "관악구", "금천구"};

            memberRepository.save(
                    Member.builder()
                            .memberAccount("account" + i + "@google.com")
                            .memberPassword("password" + i)
                            .memberName("김이름" + i)
                            .memberNickname("nickName" + i)
                            .memberGender(gender)
                            .memberBirth(randomDate)
                            .memberPhone("010-" + number1 + "-" + number2)
                            .memberLocation(location[i % 25])
                            .memberComment("hello world" + i)
                            .build()
            );

        }

    }

    @Test
    @DisplayName("freeBoard")
    void freeBoard() {
    }

    @Test
    @DisplayName("freeBoardReply")
    void freeBoardReply() {

        String[] names = {"강호동", "홍길동", "김철수"};

        String[] additionalNames = {
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수",
                "이영희", "박민수", "정진영", "최미영", "김영수",
                "박지영", "이동민", "김소연", "황성민", "신영자",
                "오준호", "김미경", "박진우", "조은정", "한철수"
                // 추가 이름을 여기에 계속 추가해줍니다.
        };

        String[] allNames = new String[names.length + additionalNames.length];
        System.arraycopy(names, 0, allNames, 0, names.length);
        System.arraycopy(additionalNames, 0, allNames, names.length, additionalNames.length);

        // 출력 예시
        for (int i = 0; i < allNames.length; i++) {
            System.out.println('"' + allNames[i] + '"');
        }


    }

}
