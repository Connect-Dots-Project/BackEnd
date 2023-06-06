package site.connectdots.connectdotsprj.csv.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.csv.entity.JsonData;
import site.connectdots.connectdotsprj.csv.service.ReadJsonService;

import java.io.IOException;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents")
public class JsonController {

    private final ReadJsonService jsonService;


    @GetMapping("/csv")
    public ResponseEntity<List<JsonData>> getAllProducts() {
        try {
            // ProductService를 사용하여 상품 데이터를 가져옴
            List<JsonData> products = jsonService.readJsonFile();
            System.out.println("products = " + products);
            // React로 데이터 전송
            return ResponseEntity.ok().body(products);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}