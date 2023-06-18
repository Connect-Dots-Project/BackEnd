package site.connectdots.connectdotsprj.hotplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceWriteRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceListResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceWriteResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HotplaceService {

    private final HotplaceRepository hotplaceRepository;
    private final MemberRepository memberRepository;

    @Value("${upload.path}")
    private String uploadRootPath;

    // 글 전체조회
    @Transactional(readOnly = true)
    public HotplaceListResponseDTO findAll() {
        List<Hotplace> hotplaceList = hotplaceRepository.findAllByOrderByHotplaceWriteDateDesc();

        List<HotplaceDetailResponseDTO> list = hotplaceList.stream()
                .map(HotplaceDetailResponseDTO::new)
                .collect(Collectors.toList());

        return HotplaceListResponseDTO.builder()
                .hotplaceList(list)
                .build();
    }


    // 글 작성
    public HotplaceWriteResponseDTO write(
            final HotplaceWriteRequestDTO dto,
            String uploadFilePath
            /*, final TokenUserInfo userInfo */) throws RuntimeException {

        Hotplace saved = hotplaceRepository.save(dto.toEntity(uploadFilePath));

        HotplaceDetailResponseDTO hotplaceDetailResponseDTO = new HotplaceDetailResponseDTO(saved);

        return HotplaceWriteResponseDTO.builder()
                .isWrite(hotplaceDetailResponseDTO.getHotplaceFullAddress() != null)
                .build();
    }

    // 글 삭제
    public void delete(Long hotplaceIdx) {
        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException(hotplaceIdx + "의 글을 찾을 수 없습니다."));

        hotplaceRepository.delete(hotplace);
    }


    // 글 수정
    public HotplaceDetailResponseDTO modify(final HotplaceModifyRequestDTO dto) {
        // 조회
        final Hotplace hotplaceEntity = findOne(dto.getHotplaceIdx());

        //세터
        dto.updateHotplace(hotplaceEntity);

        // 저장
        Hotplace modified = hotplaceRepository.save(hotplaceEntity);

        return new HotplaceDetailResponseDTO(modified);
    }


    private Hotplace findOne(Long hotplaceIdx) {
        Hotplace hotplaceEntity = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException(hotplaceIdx + "의 글을 찾을 수 없습니다."));
        return hotplaceEntity;
    }


    // 행정구역으로 핫플레이스 게시물 목록 조회하기
    @Transactional(readOnly = true)
    public HotplaceListResponseDTO findByLocation(Location location) {

        List<Hotplace> hotplaceList = hotplaceRepository.findByLocation(location);

        List<HotplaceDetailResponseDTO> list = hotplaceList.stream()
                .map(HotplaceDetailResponseDTO::new)
                .collect(Collectors.toList());

        return HotplaceListResponseDTO.builder()
                .hotplaceList(list)
                .build();

    }


    // 질문
    // 맵에 뿌려주는 서비스는 따로 만들까융?

    // 마커 전체보기
    public List<Hotplace> displayMarkersAll() {
        return hotplaceRepository.findAll();
    }

    // 마커 - 행정구역별 보기
    public List<Hotplace> displayMarkersByLocation(String kakaoLocation) {
//        Location selectedLocation = Location.of(kakaoLocation);
        return hotplaceRepository.findByKakaoLocation(kakaoLocation);
    }

    // 핫플레이스 이미지 파일 저장 메서드
    public String uploadHotplaceImg(MultipartFile originalFile) throws IOException {

        File rootDir = new File(uploadRootPath);
        if (!rootDir.exists()) rootDir.mkdir();

        String uniqueFileName = UUID.randomUUID() + "_" + originalFile.getOriginalFilename();

        File uploadFile = new File(uploadRootPath + "/" + uniqueFileName);
        originalFile.transferTo(uploadFile);

        return uniqueFileName;

    }
}
