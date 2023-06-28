package site.connectdots.connectdotsprj.freeboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardLike;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FreeBoardLikeRepositoryTest {

    @Autowired
    FreeBoardLikeRepository freeBoardLikeRepository;

    @Test
    @DisplayName("save")
    void saveTest() {
        //given
        String account = "test";
        Long idx = 1L;
        //when
        System.out.println("----------------------------");

        freeBoardLikeRepository.save(FreeBoardLike.builder()
                .memberAccount(account)
                .freeboardIdx(idx)
                .build());

        //then
    }

    @Test
    @DisplayName("duplicateTest")
    void duplicateTest() {
        //given
        String account = "test";
        Long idx = 1L;

        String account2 = "test";
        Long idx2 = 2L;

        //when

        FreeBoardLike byMemberAccountAndFreeboardIdx = freeBoardLikeRepository.findByMemberAccountAndFreeboardIdx(account, idx);
        FreeBoardLike byMemberAccountAndFreeboardIdx1 = freeBoardLikeRepository.findByMemberAccountAndFreeboardIdx(account2, idx2);

        System.out.println(byMemberAccountAndFreeboardIdx == null);
        System.out.println(byMemberAccountAndFreeboardIdx1 == null);

        //then
    }
}