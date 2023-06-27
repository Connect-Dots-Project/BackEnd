package site.connectdots.connectdotsprj.freeboard.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardModifyRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.*;
import site.connectdots.connectdotsprj.freeboard.service.FreeBoardService;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;

import java.util.List;


@RestController
@RequestMapping("/contents/free-board")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<FreeBoardDetailResponseDTO> detailViewById(
            @PathVariable(name = "freeBoardIdx") Long freeBoardIdx,
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo) {

        System.out.println("\n\n\n\n--------------------------------------------");
        System.out.println(jwtUserInfo);
        FreeBoardDetailResponseDTO foundFreeBoardDetail = freeBoardService.detailView(freeBoardIdx, jwtUserInfo);

        return ResponseEntity.ok().body(foundFreeBoardDetail);
    }

    /**
     * 글을 작성해주는 메서드
     *
     * @param dto
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> writeFreeBoard(
            @RequestPart("freeBoard") FreeBoardWriteRequestDTO dto,
            @RequestPart("freeBoardImg") MultipartFile freeBoardImg,
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo
            , BindingResult result) {

        String uploadFilePath = null;
        if (freeBoardImg != null) {
            try {
                uploadFilePath = freeBoardService.uploadFreeBoardImg(freeBoardImg);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            List<FreeBoardResponseDTO> freeBoardResponseDTO = freeBoardService.writeFreeBoard(dto, jwtUserInfo, uploadFilePath);
            return ResponseEntity.ok().body(freeBoardResponseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    /**
     * 자유게시판에 댓글을 달아주는 메서드
     *
     * @param dto
     * @return
     */
    @PostMapping("/replies")
    public ResponseEntity<List<FreeBoardDetailReplyDTO>> writeReplyByFreeBoard(
            @RequestBody FreeBoardReplyWriteRequestDTO dto,
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo
    ) {
        List<FreeBoardDetailReplyDTO> freeBoardDetailReplyDTO = freeBoardService.writeReplyByFreeBoard(dto, jwtUserInfo);

        return ResponseEntity.ok().body(freeBoardDetailReplyDTO);
    }

    /**
     * 작성한 글을 수정하는 메서드
     *
     * @return
     */
    @PatchMapping()
    public ResponseEntity<FreeBoardDetailResponseDTO> modifyFreeBoard(
            @AuthenticationPrincipal JwtUserInfo userInfo
            , @RequestBody FreeBoardModifyRequestDTO dto
    ) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.modifyFreeBoard(dto, userInfo);

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDeleteResponseDTO> deleteFreeBoard(
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo
            , @PathVariable(name = "freeBoardIdx") Long freeBoardIdx
    ) {

        System.out.println("\n\n\n\n\n-----------------------------------");
        System.out.println(jwtUserInfo);
        System.out.println(freeBoardIdx);

        System.out.println("-----------------------------------\n\n\n\n");

        FreeBoardDeleteResponseDTO freeBoardDeleteResponseDTO = freeBoardService.delete(jwtUserInfo, freeBoardIdx);

        return ResponseEntity.ok().body(freeBoardDeleteResponseDTO);
    }


    /**
     * 자유게시판의 좋아요 or 취소 기능
     *
     * @param freeBoardIdx
     * @return
     */
    @PostMapping("/like/{freeBoardIdx}")
    public ResponseEntity<FreeBoardLikeResultResponseDTO> updateLike(
            @AuthenticationPrincipal JwtUserInfo userInfo
            , @PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardLikeResultResponseDTO responseDTO = freeBoardService.updateLikeCount(freeBoardIdx, userInfo.getAccount());

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
            @AuthenticationPrincipal JwtUserInfo userInfo
    ) {
        List<FreeBoardResponseDTO> freeBoardResponseList = freeBoardService.myPageFindAll(userInfo.getAccount());

        return ResponseEntity.ok().body(freeBoardResponseList);
    }

    // 입력값 검증
    private ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("입력값 검증에 걸림!!!!!!!!!!!! invalid client data - {}", err.toString());
            });

            return ResponseEntity.badRequest().body(fieldErrors);
        }
        return null;
    }

}
