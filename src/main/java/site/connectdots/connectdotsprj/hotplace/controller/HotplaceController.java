package site.connectdots.connectdotsprj.hotplace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceWriteRequestDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.service.HotplaceService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents/hot-place")
public class HotplaceController {

    private final HotplaceService hotplaceService;

    // 글 전체조회
    @GetMapping
    public ResponseEntity<?> list() {
        List<Hotplace> hotplaceList = hotplaceService.findAll();
        log.info("HotplaceController.list.info 글 전체조회 {} ", hotplaceList);
        return ResponseEntity.ok().body(hotplaceList);
    }

    // 글 작성
    @PostMapping
    public ResponseEntity<?> write(@Validated @RequestBody HotplaceWriteRequestDTO dto, BindingResult result) {
        log.info("{}", dto);
        Hotplace hotplace = null;
        try {
            hotplace = hotplaceService.write(dto);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        log.info("HotplaceController.write.info 글 작성 {}, {}", dto, result);
        return ResponseEntity.ok().body(hotplace);
    }


    // 글 삭제
    @DeleteMapping("/{hotplaceIdx}")
    public ResponseEntity<?> delete(@PathVariable Long hotplaceIdx) {
        hotplaceService.delete(hotplaceIdx);
        log.info("HotplaceController.delete.info 글 삭제 {}", hotplaceIdx);
        return ResponseEntity.ok().build();
    }


    // 글 수정
    @PatchMapping("/{hotplaceIdx}")
    public ResponseEntity<?> modify(@Validated @PathVariable Long hotplaceIdx, @RequestBody HotplaceModifyRequestDTO dto) {
        Hotplace modifiedHotplace = hotplaceService.modify(hotplaceIdx, dto);
        log.info("HotplaceController.modify.info 글 수정 {}, {}", hotplaceIdx, dto);
        return ResponseEntity.ok().body(modifiedHotplace);
    }

    // 포스트맨 응답이 짤려서 나옴..


    // 위도경도
    // 좋아요
    // 글 조회시 구분해서 - 행정구역별, 지도형태별, 게시글형태별
    // 사진 저장 경로설정



}
