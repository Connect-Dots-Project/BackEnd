package site.connectdots.connectdotsprj.csv.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import site.connectdots.connectdotsprj.csv.entity.JsonData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReadJsonService {

    public List<JsonData> readJsonFile() throws IOException {
//        3개의 파일을 읽어서 하나로 합침
        List<JsonData> csvMergedData = new ArrayList<>();

        String[] jsonFilePath = {
                "C:\\BackEnd\\src\\main\\resources\\static\\json_csv\\GS25.json",
                "C:\\BackEnd\\src\\main\\resources\\static\\json_csv\\CUcsv.json",
                "C:\\BackEnd\\src\\main\\resources\\static\\json_csv\\7eleven.json"
        };

        // ObjectMapper를 사용하여 JSON 파일을 읽고 Product 리스트로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        for (String s : jsonFilePath) {
            List<JsonData> data =
                    objectMapper.readValue(new File(s), new TypeReference<>() {});
            csvMergedData.addAll(data);
        }

        //가공 작업

        return csvMergedData;
    }

}
