package site.connectdots.connectdotsprj.freeboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardCategory;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FreeBoardRepositoryTest {
    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("전체 조회에 성공할 것이며 데이터의 사이즈는 50일 것이다.")
    void findAllTest() {
        // given
        // when
        List<FreeBoard> freeBoardList = freeBoardRepository.findAll();

        List<FreeBoardResponseDTO> responseList = freeBoardList.stream()
                .map(FreeBoardResponseDTO::new)
                .collect(Collectors.toList());

        responseList.forEach(System.out::println);

        // then

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
        Long memberIdx = 13L;

        //when
        List<FreeBoard> findAllByMemberIdx = freeBoardRepository.findAllByMemberMemberIdx(memberIdx);
        findAllByMemberIdx.forEach(System.out::println);

        //then
        assertEquals("친목", findAllByMemberIdx.get(0).getFreeBoardCategory().toString());

    }

    @Test
    @DisplayName("자유게시판 작성에 성공할 것이다.")
    void writeFreeBoardTest() {


        FreeBoardWriteRequestDTO dto = FreeBoardWriteRequestDTO.builder()
                .freeBoardTitle("jjj제목!!!TEST")
                .freeBoardContent("hhh내용!!!TEST")
                .freeBoardCategory(FreeBoardCategory.잡담)
                .freeBoardLocation("강북구")
                .freeBoardImg("null")
                .memberIdx(15L)
                .build();

        //given

        //when
        freeBoardRepository.save(
                FreeBoard.builder()
                        .freeBoardTitle(dto.getFreeBoardTitle())
                        .freeBoardContent(dto.getFreeBoardContent())
                        .freeBoardImg(dto.getFreeBoardImg())
                        .freeBoardLocation(dto.getFreeBoardLocation())
                        .freeBoardCategory(dto.getFreeBoardCategory())
                        .member(memberRepository.findById(dto.getMemberIdx()).orElseThrow())
                        .build()
        );
        //then

    }


    @Test
    @DisplayName("정렬 기준 테스트")
    void tetst() {
        //given

        List<FreeBoard> allByOrderByFreeBoardIdx = freeBoardRepository.findAllByOrderByFreeBoardIdxDesc();
        for (FreeBoard byOrderByFreeBoardIdx : allByOrderByFreeBoardIdx) {
            System.out.println(byOrderByFreeBoardIdx);
        }
        //when


        //then
    }

}