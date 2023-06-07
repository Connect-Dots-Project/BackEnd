package site.connectdots.connectdotsprj.freeboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FreeBoardReplyRepositoryTest {

    @Autowired
    FreeBoardReplyRepository replyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Test
    @DisplayName("13번 게시글을 찾았을 때 댓글의 수는 12개일 것이다.")
    void findAllByFreeBoardIdxTest() {
        //given
        Long freeBoardIdx = 13L;

        //when
        List<FreeBoardReply> freeBoardReplyList = replyRepository.findAllByFreeBoardFreeBoardIdx(freeBoardIdx);
        List<FreeBoardDetailReplyDTO> collect = freeBoardReplyList.stream()
                .map(FreeBoardDetailReplyDTO::new)
                .collect(Collectors.toList());

        //then
        assertEquals(12, collect.size());
    }

    @Test
    @DisplayName("댓글 작성에 성공할 것이다.")
    @Rollback
    void freeBoardReplyWriteTest() {
        //given
        Long memberIdx = 5L;
        Long freeBoardIdx = 5L;
        String content = "테스트에서 추가된 댓글입니다!!";

        //when
        FreeBoardReply save = replyRepository.save(
                FreeBoardReply.builder()
                        .member(
                                memberRepository.findById(memberIdx).orElseThrow()
                        )
                        .freeBoard(
                                freeBoardRepository.findById(freeBoardIdx).orElseThrow()
                        )
                        .freeBoardReplyContent(content)
                        .build()
        );

        //then
        assertEquals("테스트에서 추가된 댓글입니다!!", save.getFreeBoardReplyContent());

    }

}