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
    public ResponseEntity<List<CvsResponseDTO>> getCvsData(){
        List<CvsResponseDTO> cvs = service.findAll();
        return ResponseEntity.ok().body(cvs);
    }
//    @GetMapping("/{cvsType}")
//    public ResponseEntity<List<CvsResponseDTO>> getDefaultCvsData(@PathVariable(required = false) String cvsType) {
//        if (cvsType == null || cvsType.isEmpty()) {
//            cvsType = "GS25"; // 기본 편의점 타입으로 설정합니다.
//        }
//
//        try {
//            List<CvsResponseDTO> filteredData = service.filterCvs(cvsType, null); // 두 번째 경로 변수인 cvsSale은 null로 전달합니다.
//            return ResponseEntity.ok().body(filteredData);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }

//    @GetMapping("/{cvsType}/{cvsSale}")
//    public ResponseEntity<List<CvsResponseDTO>> getFilteredCvsData(@PathVariable String cvsType, @PathVariable String cvsSale) {
//        try {
//            List<CvsResponseDTO> filteredData = service.filterCvs(cvsType, cvsSale);
//            return ResponseEntity.ok().body(filteredData);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }

//    @PostMapping("/data")
//    public ResponseEntity<String> saveCvsData() {
//        try {
//            service.saveCvsData();
//            return ResponseEntity.ok().body("db에 저장 성공");
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .body(e.getMessage());
//        }
//    }


//    @GetMapping("/{cvsType}")
//    public ResponseEntity<List<CvsResponseDTO>> getCvsData(@PathVariable String cvsType){
//        List<CvsResponseDTO> cvs = service.findAllByCvsType(cvsType);
//        return ResponseEntity.ok().body(cvs);
//    }


}


//    @GetMapping()
//    public ResponseEntity<List<Cvs>> getAllProducts() {
//        try {
//            // ProductService를 사용하여 상품 데이터를 가져옴
//            List<Cvs> products = service.readJsonFile();
//            System.out.println("products = " + products);
//            // React로 데이터 전송
//            return ResponseEntity.ok().body(products);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }

//    @GetMapping("/{cvsName}")
//    public ResponseEntity<List<Cvs>> getCsvData(@PathVariable String cvsName) {
//        try {
//            List<Cvs> filteredCsv = service.filterCsv(cvsName);
//
//            return ResponseEntity.ok().body(filteredCsv);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }


