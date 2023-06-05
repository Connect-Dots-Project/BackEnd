package site.connectdots.connectdotsprj.freeboard.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
