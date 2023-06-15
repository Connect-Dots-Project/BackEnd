package site.connectdots.connectdotsprj.cvs.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.cvs.entity.Cvs;
import site.connectdots.connectdotsprj.cvs.service.CvsService;

import java.io.IOException;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents/cvs")
public class CvsController {

    private final CvsService service;

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


