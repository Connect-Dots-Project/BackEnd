package site.connectdots.connectdotsprj.cvs.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.cvs.dto.CvsResponseDTO;
import site.connectdots.connectdotsprj.cvs.service.CvsService;

import java.io.IOException;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents/cvs")
@CrossOrigin("http://localhost:3000")
public class CvsController {

    private final CvsService service;

    @GetMapping()
    public ResponseEntity<List<CvsResponseDTO>> getCvsData() {
        List<CvsResponseDTO> cvs = service.findAll();
        return ResponseEntity.ok().body(cvs);
    }
    @PostMapping("/data")
    public ResponseEntity<String> saveCvsData() {
        try {
            service.saveCvsData();
            return ResponseEntity.ok().body("db에 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }

//        @GetMapping("/{cvsType}")
//    public ResponseEntity<List<CvsResponseDTO>> getCvsData(@PathVariable String cvsType){
//        List<CvsResponseDTO> cvs = service.findAllByCvsType(cvsType);
//        return ResponseEntity.ok().body(cvs);
//    }
}




