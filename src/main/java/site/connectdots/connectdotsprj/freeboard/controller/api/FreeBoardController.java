package site.connectdots.connectdotsprj.freeboard.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.freeboard.dto.request.FreeBoardReplyWriteRequestDTO;
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

    @GetMapping("/free-board")
    public ResponseEntity<List<FreeBoardResponseDTO>> findAll() {
        List<FreeBoardResponseDTO> freeBoardList = freeBoardService.findAll();

        return ResponseEntity.ok().body(freeBoardList);
    }

    @GetMapping("/free-board/{freeBoardIdx}")
    public ResponseEntity<FreeBoardDetailResponseDTO> findById(@PathVariable(name = "freeBoardIdx") Long freeBoardIdx) {
        FreeBoardDetailResponseDTO foundFreeBoardDetail = freeBoardService.findById(freeBoardIdx);

        return ResponseEntity.ok().body(foundFreeBoardDetail);
    }

    @PostMapping("/free-board/replies")
    public ResponseEntity<List<FreeBoardDetailReplyDTO>> writeReplyByFreeBoard(@RequestBody FreeBoardReplyWriteRequestDTO dto) {
        List<FreeBoardDetailReplyDTO> freeBoardDetailReplyDTO = freeBoardService.writeReplyByFreeBoard(dto);

        return ResponseEntity.ok().body(freeBoardDetailReplyDTO);
    }

}
