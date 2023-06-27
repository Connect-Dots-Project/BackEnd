package site.connectdots.connectdotsprj.hotplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.aws.service.S3Service;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceWriteRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceListResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceWriteResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HotplaceService {

    private final HotplaceRepository hotplaceRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final Integer START_PAGE = 0;
    private final Integer SIZE = 10;
    private final String KEY = "hotplaceIdx";


    // 이미지 로컬 저장 경로 -> 133, 134, 140, 141, 147
//    @Value("${upload.path}")
//    private String uploadRootPath;

    // 글 전체조회
    @Transactional(readOnly = true)
    public HotplaceListResponseDTO findAll(Integer page) {
        Sort hotplaceIdx = Sort.by(KEY);
        PageRequest pageRequest = PageRequest.of(page, SIZE, hotplaceIdx.descending());

        Page<Hotplace> hotplaceList = hotplaceRepository.findAll(pageRequest);

        List<HotplaceDetailResponseDTO> list = hotplaceList.stream()
                .map(HotplaceDetailResponseDTO::new)
                .collect(Collectors.toList());

        return HotplaceListResponseDTO.builder()
                .hotplaceList(list)
                .build();
    }


    // 글 작성
    public HotplaceWriteResponseDTO write(
            final JwtUserInfo jwtUserInfo
            , final HotplaceWriteRequestDTO dto
            , final String uploadFilePath
    ) throws RuntimeException {


        Member member = getAccount(jwtUserInfo.getAccount());

        Hotplace saved = hotplaceRepository.save(dto.toEntity(member, uploadFilePath));

        HotplaceDetailResponseDTO hotplaceDetailResponseDTO = new HotplaceDetailResponseDTO(saved);

        return HotplaceWriteResponseDTO.builder()
                .isWrite(hotplaceDetailResponseDTO.getHotplaceFullAddress() != null)
                .build();
    }

    private Member getAccount(String account) {
        return memberRepository.findByMemberAccount(account);
    }


    // 글 삭제
    public void delete(JwtUserInfo jwtUserInfo, Long hotplaceIdx) {

        Member member = getAccount(jwtUserInfo.getAccount());

        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException(hotplaceIdx + "의 글을 찾을 수 없습니다."));

        try {
            if (member == hotplace.getMember()) {
                System.out.println("삭제 ============================제====================");
                hotplaceRepository.delete(hotplace);
            }
        } catch (Exception e) {
            log.error("삭제에 실패하였습니다.");
            e.printStackTrace();
            throw new RuntimeException("삭제에 실패하였습니다.");
        }

    }


    // 글 수정
    public HotplaceDetailResponseDTO modify(
            JwtUserInfo jwtUserInfo
            , final HotplaceModifyRequestDTO dto
            , String uploadFilePath) {

        final Hotplace hotplaceEntity = findOne(dto.getHotplaceIdx());

        Member member = getAccount(jwtUserInfo.getAccount());


        if (hotplaceEntity.getMember() == member) {
            dto.updateHotplace(member, hotplaceEntity, uploadFilePath);
            Hotplace modified = hotplaceRepository.saveAndFlush(hotplaceEntity);
            return new HotplaceDetailResponseDTO(modified);
        }
        return null;

    }



    private Hotplace findOne(Long hotplaceIdx) {
        Hotplace hotplaceEntity = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException(hotplaceIdx + "의 글을 찾을 수 없습니다."));
        return hotplaceEntity;
    }


    // 행정구역으로 핫플레이스 게시물 목록 조회하기
    @Transactional(readOnly = true)
    public HotplaceListResponseDTO findByLocation(String kakaoLocation) {

        List<Hotplace> hotplaceList = hotplaceRepository.findByKakaoLocation(kakaoLocation);

        List<HotplaceDetailResponseDTO> list = hotplaceList.stream()
                .map(HotplaceDetailResponseDTO::new)
                .collect(Collectors.toList());

        return HotplaceListResponseDTO.builder()
                .hotplaceList(list)
                .build();

    }


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

        // 루트 디렉토리 존재 확인 후, 존재하지 않으면 폴더 생성
//        File rootDir = new File(uploadRootPath);
//        if (!rootDir.exists()) rootDir.mkdir();

        // 파일명 유니크하게 변경
        String uniqueFileName = UUID.randomUUID() + "_" + originalFile.getOriginalFilename();

        // 파일 로컬에 저장하기
//        File uploadFile = new File(uploadRootPath + "/" + uniqueFileName);
//        originalFile.transferTo(uploadFile);

        // 파일 aws s3 버킷에 저장하기
        String uploadUrl = s3Service.uploadToS3Bucket(originalFile.getBytes(), uniqueFileName);

        return uploadUrl;
//        return uniqueFileName;

    }

    // 핫플레이스 이미지 저장경로 찾기
    public String getHotplacePath(String fileName) {
        String hotplaceImgPath = hotplaceRepository.findByHotplaceImg(fileName);

        return hotplaceImgPath;

    }
}