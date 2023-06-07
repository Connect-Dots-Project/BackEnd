package site.connectdots.connectdotsprj.hotplace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceWriteRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceResponseDTO;
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

        log.info("HotplaceController.write.info 글 작성 {}, {}", dto, result);
//        Hotplace hotplace = null;

        if (dto == null) {
            return ResponseEntity.badRequest().body("핫플레이스 게시물 정보를 전달해주세요!");
        }

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            HotplaceResponseDTO hotplaceResponseDTO = hotplaceService.write(dto);
            return ResponseEntity.ok().body(hotplaceResponseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    // 글 삭제
    @DeleteMapping("/{hotplaceIdx}")
    public ResponseEntity<?> delete(@PathVariable Long hotplaceIdx) {
        log.info("HotplaceController.delete.info 글 삭제 {}", hotplaceIdx);

        try {
            hotplaceService.delete(hotplaceIdx);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    // 글 수정
    @PatchMapping("/{hotplaceIdx}")
    public ResponseEntity<?> modify(@Validated @PathVariable Long hotplaceIdx
                                    , @RequestBody HotplaceModifyRequestDTO dto
                                    , BindingResult result) {
        log.info("HotplaceController.modify.info 글 수정 {}, {}", hotplaceIdx, dto);

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            Hotplace modifiedHotplace = hotplaceService.modify(hotplaceIdx, dto);
            return ResponseEntity.ok().body(modifiedHotplace);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 입력값 검증
    private ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("입력값 검증에 걸림!!!!!!!!!!!! invalid client data - {}", err.toString());
            });

            return ResponseEntity.badRequest().body(fieldErrors);
        }
        return null;
    }

    // 포스트맨 응답이 짤려서 나옴..


    // 위도경도
    // 좋아요
    // 글 조회시 구분해서 - 행정구역별, 지도형태별, 게시글형태별
    // 사진 저장 경로설정



}
