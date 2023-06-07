package site.connectdots.connectdotsprj.freeboard.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardWriteRequestDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.service.FreeBoardService;

import java.util.List;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    private final int LIKE = 1;
    private final int HATE = -1;


    @GetMapping("/free-board")
    public ResponseEntity<List<FreeBoardResponseDTO>> findAll() {
        List<FreeBoardResponseDTO> freeBoardList = freeBoardService.findAll();

        return ResponseEntity.ok().body(freeBoardList);
    }

    @GetMapping("/free-board/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> detailViewById(@PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO foundFreeBoardDetail = freeBoardService.findById(freeBoardIdx);

        return ResponseEntity.ok().body(foundFreeBoardDetail);
    }

    @PostMapping("/free-board")
    public ResponseEntity<List<FreeBoardResponseDTO>> writeFreeBoard(@RequestBody FreeBoardWriteRequestDTO dto) {
        List<FreeBoardResponseDTO> freeBoardResponseDTO = freeBoardService.writeFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardResponseDTO);
    }

    @PostMapping("/free-board/replies")
    public ResponseEntity<List<FreeBoardDetailReplyDTO>> writeReplyByFreeBoard(@RequestBody FreeBoardReplyWriteRequestDTO dto) {
        List<FreeBoardDetailReplyDTO> freeBoardDetailReplyDTO = freeBoardService.writeReplyByFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardDetailReplyDTO);
    }

    @PostMapping("/free-board/like/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> likeCount(@PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.updateLikeCount(freeBoardIdx, LIKE);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/free-board/hate/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> hateCount(@PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO responseDTO = freeBoardService.updateLikeCount(freeBoardIdx, HATE);

        return ResponseEntity.ok().body(responseDTO);
    }

}
