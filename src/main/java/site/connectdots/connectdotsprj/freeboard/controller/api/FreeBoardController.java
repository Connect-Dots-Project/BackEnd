package site.connectdots.connectdotsprj.freeboard.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardModifyRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.service.FreeBoardService;
import site.connectdots.connectdotsprj.global.config.TokenUserInfo;

import java.util.List;

@RestController
@RequestMapping("/contents/free-board")
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final int LIKE = 1;
    private final int HATE = -1;


    /**
     * 전체 조회
     *
     * @param page : 가져올 페이지
     * @return : 1페이지 당 10개의 게시글을 가장 최신 작서된 순으로 리턴
     */
    @GetMapping("/{page}")
    public ResponseEntity<List<FreeBoardResponseDTO>> findAll(@PathVariable(name = "page") Integer page) {
        List<FreeBoardResponseDTO> freeBoardList = freeBoardService.findAll(page);

        return ResponseEntity.ok().body(freeBoardList);
    }

    /**
     * 자유게시판의 글을 클릭했을 때 상세 보기
     *
     * @param freeBoardIdx : 해당 게시글의 인덱스
     * @return : 해당 글 + 리플을 리턴
     */
    @GetMapping("/detail/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> detailViewById(@PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO foundFreeBoardDetail = freeBoardService.detailView(freeBoardIdx);

        return ResponseEntity.ok().body(foundFreeBoardDetail);
    }

    /**
     * 글을 작성해주는 메서드
     *
     * @param dto
     * @return
     */
    @PostMapping()
    public ResponseEntity<List<FreeBoardResponseDTO>> writeFreeBoard(@RequestBody FreeBoardWriteRequestDTO dto) {
        System.out.println("\n\n\n\n----------------------writeFreeBoard----------------------------");
        System.out.println(dto);
        System.out.println("-----------------------writeFreeBoard---------------------------\n\n\n\n");
        List<FreeBoardResponseDTO> freeBoardResponseDTO = freeBoardService.writeFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardResponseDTO);
    }

    /**
     * 자유게시판에 댓글을 달아주는 메서드
     *
     * @param dto
     * @return
     */
    @PostMapping("/replies")
    public ResponseEntity<List<FreeBoardDetailReplyDTO>> writeReplyByFreeBoard(@RequestBody FreeBoardReplyWriteRequestDTO dto) {
        System.out.println("\n\n\n\n-------------------------writeReplyByFreeBoard-------------------------");
        System.out.println(dto);
        System.out.println("------------------------writeReplyByFreeBoard--------------------------\n\n\n\n");
        List<FreeBoardDetailReplyDTO> freeBoardDetailReplyDTO = freeBoardService.writeReplyByFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardDetailReplyDTO);
    }

    /**
     * 작성한 글을 수정하는 메서드
     *
     * @return
     */
    @PatchMapping()
    public ResponseEntity<FreeBoardDetailResponseDTO> modifyFreeBoard(
            @AuthenticationPrincipal TokenUserInfo userInfo
            , @RequestBody FreeBoardModifyRequestDTO dto
    ) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.modifyFreeBoard(dto, userInfo);

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{freeBoardIdx}")
    public ResponseEntity<?> deleteFreeBoard(
            @AuthenticationPrincipal TokenUserInfo userInfo
            , @PathVariable(name = "freeBoardIdx") Long freeBoardIdx
    ) {

        return null;
    }


    /**
     * 자유게시판의 좋아요 기능
     *
     * @param freeBoardIdx
     * @return
     */
    @PostMapping("/like/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> likeCount(
            @AuthenticationPrincipal TokenUserInfo userInfo
            , @PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.updateLikeCount(freeBoardIdx, LIKE, userInfo.getAccount());

        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * 자유게시판의 싫어요 기능
     *
     * @param freeBoardIdx
     * @return
     */
    @PostMapping("/hate/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> hateCount(
            @AuthenticationPrincipal TokenUserInfo userInfo
            , @PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.updateLikeCount(freeBoardIdx, HATE, userInfo.getAccount());

        return ResponseEntity.ok().body(responseDTO);
    }


    /**
     * 마이페이지에서 내가 작성한 자유게시판 글을 볼 때 사용하는 메서드
     *
     * @param userInfo
     * @return
     */
    @GetMapping("/my-page/free-board")
    public ResponseEntity<List<FreeBoardResponseDTO>> myPageFreeBoardList(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        List<FreeBoardResponseDTO> freeBoardResponseList = freeBoardService.myPageFindAll(userInfo.getAccount());

        return ResponseEntity.ok().body(freeBoardResponseList);
    }

}
