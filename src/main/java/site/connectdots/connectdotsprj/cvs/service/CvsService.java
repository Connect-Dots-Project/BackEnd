package site.connectdots.connectdotsprj.cvs.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.cvs.dto.CvsResponseDTO;
import site.connectdots.connectdotsprj.cvs.entity.Cvs;
import site.connectdots.connectdotsprj.cvs.repository.CvsRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CvsService {

    private final CvsRepository repository;

    private final String[] jsonFilePath = {
            "C:\\BackEnd-fork\\src\\main\\resources\\static\\json_csv\\GS25.json",
            "C:\\BackEnd-fork\\src\\main\\resources\\static\\json_csv\\CUcsv.json",
            "C:\\BackEnd-fork\\src\\main\\resources\\static\\json_csv\\7eleven.json"
    };

    //json 파일들 읽고 db에 저장
    private List<CvsResponseDTO> readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }

    public void saveCvsData() throws IOException {
        for (String filePath : jsonFilePath) {
            List<CvsResponseDTO> data = readJsonFile(filePath);

            for (CvsResponseDTO dto : data) {
                Cvs cvsEntity = new Cvs();
                cvsEntity.setCvsImg(dto.getImg());
                cvsEntity.setCvsTitle(dto.getTitle());
                cvsEntity.setCvsPrice(dto.getPrice());
                cvsEntity.setCvsSale(dto.getSale());
                cvsEntity.setCvsType(dto.getCvs());

                repository.save(cvsEntity);
            }
        }
    }


}

//
//    public List<Cvs> readJsonFile() throws IOException {
//        List<Cvs> csvMergedData = new ArrayList<>();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        for (String filePath : jsonFilePath) {
//            List<Cvs> data = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
//            csvMergedData.addAll(data);
//        }
//
//        return csvMergedData;
//    }
//
//    // 웹페이지에서 특정 편의점으로
//    public List<Cvs> filterCsv(String csv) throws IOException {
//        List<Cvs> cvs = readJsonFile();
//        return cvs.stream()
//                .filter(data -> data.getCvsType().equals(csv))
//                .collect(Collectors.toList());
//    }