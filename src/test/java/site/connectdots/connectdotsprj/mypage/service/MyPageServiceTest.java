package site.connectdots.connectdotsprj.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageBasicDTO;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyPageServiceTest {
    @Autowired
    private MyPageService myPageService;

//    @Test
//    @DisplayName("member_idx가 13번인 사람의 쓴 글을 조회하면 10개가 나온다")
//    @Transactional
//        // 트랜잭션 추가
//    void memberWriteTest() {
//        // Given
//        Long memberIdx = 13L;
//
//        // When
//        MyPageBasicDTO myPageWriteDTO = myPageService.findById(memberIdx);
//        List<FreeBoard> freeBoardList = myPageWriteDTO.getFreeBoardList();
//
//        // Then
//        assertEquals(10, freeBoardList.size(), "쓴 글의 개수는 10개여야 합니다.");
//    }

//    @Test
//    @DisplayName("member_idx가 13번인 사람의 쓴 댓글을 조회하면 110개가 나온다")
//    @Transactional
//    void memberReplytest() {
//        Long memberIdx = 13L;
//
//        // When
//        MyPageBasicDTO myPageWriteDTO = myPageService.findById(memberIdx);
//        List<FreeBoardReply> freeBoardReplyList = myPageWriteDTO.getFreeBoardReplyList();
//
//        // Then
//        assertEquals(110, freeBoardReplyList.size(), "쓴 댓글의 개수는 110개여야 한다.");
//    }


    }
