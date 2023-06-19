package site.connectdots.connectdotsprj.hotplace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceWriteRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceListResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceWriteResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.service.HotplaceService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents/hot-place")
public class HotplaceController {

    private final HotplaceService hotplaceService;

    @Value("${upload.path}")
    private String uploadRootPath;

    // 글 전체조회
    @GetMapping
    public ResponseEntity<?> list() {
        log.info("전체조회!");

        try {
            HotplaceListResponseDTO hotplaceList = hotplaceService.findAll();
            log.info("HotplaceController.list.info 글 전체조회 {} ", hotplaceList);
            return ResponseEntity.ok().body(hotplaceList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    // 글 작성
    @PostMapping
    public ResponseEntity<?> write(
            /* @AuthenticationPrincipal TokenUserInfo userInfo */
            @Validated @RequestPart("hotplace") HotplaceWriteRequestDTO dto
            , @RequestPart("hotplaceImg") MultipartFile hotplaceImg
            , BindingResult result) {

        log.info("HotplaceController.write.info 글 작성 {}, {}", dto, result);
        if (dto == null) {
            return ResponseEntity.badRequest().body("핫플레이스 게시물 정보를 전달해주세요!");
        }


        String uploadFilePath = null;
        if (hotplaceImg != null) {
            log.info("attached file name ======================================: {}", hotplaceImg.getOriginalFilename());
            try {
                uploadFilePath = hotplaceService.uploadHotplaceImg(hotplaceImg);
            } catch (Exception e) {
                log.warn("파일처리 예외가 발생했습니다.");
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            HotplaceWriteResponseDTO hotplaceWriteResponseDTO = hotplaceService.write(dto, uploadFilePath);
            return ResponseEntity.ok().body(hotplaceWriteResponseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 테스트용 - 웹페이지에서 지도 검색 (JSP)
    @GetMapping("/search")
    public ModelAndView showMapPage() {
        return new ModelAndView("/WEB-INF/map.jsp");
    }

    // 테스트용 - 웹페이지 마커 표시하ㅙ (JSP)
    @GetMapping("/map")
    public ModelAndView showHotplaceAll(Model model) {
        List<Hotplace> hotplaceList = hotplaceService.displayMarkersAll();
        model.addAttribute("hotplaceList", hotplaceList);
        return new ModelAndView("/WEB-INF/location.jsp");
    }


    @GetMapping("/map/{kakaolocation}")
    public ModelAndView showHotplaceByLocation(@PathVariable String kakaolocation, Model model) {
        List<Hotplace> hotplaceList = hotplaceService.displayMarkersByLocation(kakaolocation);
        model.addAttribute("hotplaceList", hotplaceList);
        return new ModelAndView("/WEB-INF/location.jsp");
    }


    // 글 삭제
    @DeleteMapping("/{hotplaceIdx}")
    public ResponseEntity<?> delete(@PathVariable Long hotplaceIdx) {
        log.info("HotplaceController.delete.info 글 삭제 {}", hotplaceIdx);

        try {
            hotplaceService.delete(hotplaceIdx);
            return ResponseEntity.ok("정상적으로 삭제되었습니다!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    // 글 수정
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> modify(@Validated @RequestBody HotplaceModifyRequestDTO dto
            , BindingResult result) {
        log.info("HotplaceController.modify.info 글 수정 {}", dto);

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            HotplaceDetailResponseDTO modifiedHotplace = hotplaceService.modify(dto);
            return ResponseEntity.ok().body(modifiedHotplace);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
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

    // 행정구역으로 핫플레이스 게시물 목록 조회하기
    @GetMapping("/{location}")
    public ResponseEntity<?> getHotplaceByLocation(@PathVariable Location location) {

        log.info("행정구역!");
        HotplaceListResponseDTO hotplaceList = hotplaceService.findByLocation(location);
        log.info("HotplaceController.locationList.info 행정구역별 글 전체조회 {} ", hotplaceList);
        return ResponseEntity.ok().body(hotplaceList);
    }


    // 이미지 파일 클라이언트에게 제공
    @GetMapping("/img/{fileName}")
    public ResponseEntity<?> getImg(@PathVariable String fileName) throws IOException {

        log.info("hotplaceController.getImg - GET : {}", fileName);

        try {
            // 클라이언트 요청 사진 응답
            // 1. 클라이언트 요청 파일명 앞에 업로드디렉토리를 붙여서 해당파일이 존재한 풀 경로를 만들고
            String filePath = uploadRootPath + '/' + fileName;

            // 2. 풀경로를 통해 파일 객체 생성
            File hotplaceFile = new File(filePath);

            if (!hotplaceFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileData = FileCopyUtils.copyToByteArray(hotplaceFile);

            HttpHeaders headers = new HttpHeaders();
            MediaType contentType = findExtendsionAndFetMediaType(filePath);

            if (contentType == null) {
                return ResponseEntity.internalServerError()
                        .body("발견된 파일은 이미지가 아닙니다!");
            }

            headers.setContentType(contentType);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("파일을 찾을 수가 없습니다.");
        }


    }

    // 파일경로에서 확장자 추출하기
    private MediaType findExtendsionAndFetMediaType(String filePath) {
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
        switch (ext.toUpperCase()) {
            case "JPEG" :
            case "JPG" :
                return MediaType.IMAGE_JPEG;
            case "PNG" :
                return MediaType.IMAGE_PNG;
            case "GIF" :
                return MediaType.IMAGE_GIF;
            default:
                return null;
        }
    }
}
