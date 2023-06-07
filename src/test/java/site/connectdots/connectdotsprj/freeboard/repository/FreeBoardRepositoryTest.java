package site.connectdots.connectdotsprj.freeboard.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardCategory;
import site.connectdots.connectdotsprj.member.entity.Gender;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FreeBoardRepositoryTest {
    @Autowired
    FreeBoardRepository freeBoardRepository;

//    @BeforeEach
//    void set() {
//
//        Member member1 = Member.builder()
//                .memberAccount("account1" + "@google.com")
//                .memberPassword("password")
//                .memberName("name1")
//                .memberNickname("nickName1")
//                .memberGender(Gender.M)
//                .memberBirth(LocalDateTime.now())
//                .memberPhone("010-1234-1234")
//                .memberLocation("강남구")
//                .memberComment("hello world1")
//                .build();
//
//        Member member2 = Member.builder()
//                .memberAccount("account2" + "@google.com")
//                .memberPassword("password")
//                .memberName("name2")
//                .memberNickname("nickName2")
//                .memberGender(Gender.M)
//                .memberBirth(LocalDateTime.now())
//                .memberPhone("010-5678-5678")
//                .memberLocation("강동구")
//                .memberComment("hello world2")
//                .build();
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        FreeBoard freeBoard1 = FreeBoard.builder()
//                .freeBoardTitle("title1")
//                .freeBoardContent("content1")
//                .freeBoardLocation("강동구")
//                .freeBoardCategory(FreeBoardCategory.잡담.toString())
//                .member(member1)
//                .build();
//
//        FreeBoard freeBoard2 = FreeBoard.builder()
//                .freeBoardTitle("title2")
//                .freeBoardContent("content2")
//                .freeBoardLocation("강동구")
//                .freeBoardCategory(FreeBoardCategory.놀거리.toString())
//                .member(member1)
//                .build();
//
//
//        FreeBoard freeBoard3 = FreeBoard.builder()
//                .freeBoardTitle("title3")
//                .freeBoardContent("content3")
//                .freeBoardLocation("강북구")
//                .freeBoardCategory(FreeBoardCategory.맛집탐방.toString())
//                .member(member2)
//                .build();
//
//        FreeBoard freeBoard4 = FreeBoard.builder()
//                .freeBoardTitle("title4")
//                .freeBoardContent("content4")
//                .freeBoardLocation("강동구")
//                .freeBoardCategory(FreeBoardCategory.봉사활동.toString())
//                .member(member2)
//                .build();
//
//        freeBoardRepository.save(freeBoard1);
//        freeBoardRepository.save(freeBoard2);
//        freeBoardRepository.save(freeBoard3);
//        freeBoardRepository.save(freeBoard4);
//    }


    @Test
    @DisplayName("bulk data")
//    @Rollback
    void insertBulk() {
        String[] location = {"강남구", "강북구", "강동구", "강서구"};
        FreeBoardCategory[] freeBoardCategories = {FreeBoardCategory.잡담, FreeBoardCategory.친목, FreeBoardCategory.동네정보, FreeBoardCategory.맛집탐방};

        for (int i = 1; i <= 50; i++) {
            freeBoardRepository.save(
                    FreeBoard.builder()
                            .freeBoardTitle("title" + i)
                            .freeBoardContent("content" + i)
                            .freeBoardLocation(location[i % 4])
                            .freeBoardCategory(freeBoardCategories[i % 4].toString())
                            .member(Member.builder()
                                    .memberIdx((long) i)
                                    .build())
                            .build()
            );

        }
    }


    @Test
    @DisplayName("전체 조회에 성공할 것이며 데이터의 사이즈는 50일 것이다.")
    void findAllTest() {
        // given
        // when
        List<FreeBoard> all = freeBoardRepository.findAll();
        // then
        all.forEach(System.out::println);

    }

    @Test
    @DisplayName("게시물 20번의 조회를 성공할 것이다.")
    void findByIdTest() {
        //given
        Long boardIdx = 20L;

        //when
        FreeBoard freeBoard = freeBoardRepository.findById(boardIdx).orElseThrow();
        System.out.println(freeBoard);
        //then

        assertEquals("content20", freeBoard.getFreeBoardContent());

    }


    @Test
    @DisplayName("자유게시판의 작성자를 통해 작성자의 정보를 가져오기")
    void findMemberByFreeBoardTest() {
        //given
        Long freeBoardIdx = 13L;

        //when
        FreeBoard freeBoard = freeBoardRepository.findById(freeBoardIdx).orElseThrow();

        Member member = freeBoard.getMember();
        System.out.println(member);
        //then
        assertEquals("hello world13", member.getMemberComment());
        assertEquals("010-5768-7130", member.getMemberPhone());
    }

}