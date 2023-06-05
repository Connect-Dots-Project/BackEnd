package site.connectdots.connectdotsprj.freeboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FreeBoardReplyRepositoryTest {

    @Autowired
    FreeBoardReplyRepository replyRepository;


    @Test
    @DisplayName("bulk")
    @Rollback
    void bulk() {
        //given

        for (int i = 1; i <= 5000; i++) {
            int idx = (int) (Math.random() * 50) + 1;
            replyRepository.save(
                    FreeBoardReply.builder()
                            .freeBoardReplyContent("댓글" + i)
                            .member(Member.builder()
                                    .memberIdx((long) idx)
                                    .build())
                            .freeBoard(FreeBoard.builder()
                                    .freeBoardIdx((long) idx)
                                    .build())
                            .build()
            );
        }

        //when

        //then
    }

    @Test
    @DisplayName("전체 조회에 성공할 것이며 갯수는 5000개이다")
    void findAllTest() {
        //given
        //when
        List<FreeBoardReply> findAll = replyRepository.findAll();

        //then
        assertEquals(5000, findAll.size());
    }

    @Test
    @DisplayName("게시물 번호로 조회에 성공할 것이다.")
    void findAllByFreeBoardIdxTest() {
        //given
        Long freeBoardIdx = 13L;

        //when
        List<FreeBoardReply> freeBoardReplyList = replyRepository.findAllByFreeBoardFreeBoardIdx(freeBoardIdx);
        List<FreeBoardDetailReplyDTO> collect = freeBoardReplyList.stream()
                .map(FreeBoardDetailReplyDTO::new)
                .collect(Collectors.toList());


        //then
        assertEquals(107, collect.size());
    }

}