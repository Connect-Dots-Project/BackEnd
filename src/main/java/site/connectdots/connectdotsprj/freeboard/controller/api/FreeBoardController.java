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
@RequestMapping("/contents")
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    private final int LIKE = 1;
    private final int HATE = -1;

    /**
     * 자유게시판을 눌렀을 때 모든 글을 보여주는 메서드
     *
     * @return
     */
    @GetMapping("/free-board")
    public ResponseEntity<List<FreeBoardResponseDTO>> findAll() {
        List<FreeBoardResponseDTO> freeBoardList = freeBoardService.findAll();

        return ResponseEntity.ok().body(freeBoardList);
    }

    /**
     * 자유게시판의 글을 클릭했을 때 상세 보기
     *
     * @param freeBoardIdx
     * @return
     */
    @GetMapping("/free-board/{freeBoardIdx}")
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
    @PostMapping("/free-board")
    public ResponseEntity<List<FreeBoardResponseDTO>> writeFreeBoard(@RequestBody FreeBoardWriteRequestDTO dto) {
        List<FreeBoardResponseDTO> freeBoardResponseDTO = freeBoardService.writeFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardResponseDTO);
    }

    /**
     * 자유게시판에 댓글을 달아주는 메서드
     *
     * @param dto
     * @return
     */
    @PostMapping("/free-board/replies")
    public ResponseEntity<List<FreeBoardDetailReplyDTO>> writeReplyByFreeBoard(@RequestBody FreeBoardReplyWriteRequestDTO dto) {
        List<FreeBoardDetailReplyDTO> freeBoardDetailReplyDTO = freeBoardService.writeReplyByFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardDetailReplyDTO);
    }

    /**
     * 작성한 글을 수정하는 메서드
     *
     * @return
     */
    @PatchMapping("/free-board")
    public ResponseEntity<FreeBoardDetailResponseDTO> modifyFreeBoard(
            @AuthenticationPrincipal TokenUserInfo userInfo
            , @RequestBody FreeBoardModifyRequestDTO dto
    ) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.modifyFreeBoard(dto, userInfo);

        return ResponseEntity.ok().body(responseDTO);
    }


    /**
     * 자유게시판의 좋아요 기능
     *
     * @param freeBoardIdx
     * @return
     */
    @PostMapping("/free-board/like/{freeBoardIdx}")
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
    @PostMapping("/free-board/hate/{freeBoardIdx}")
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
