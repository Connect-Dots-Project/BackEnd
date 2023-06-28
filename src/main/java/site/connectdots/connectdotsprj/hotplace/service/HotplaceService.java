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
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDeleteResponseDTO;
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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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
        // TODO 토큰에서 꺼낸 account or nickname 이 고유하기 때문에
        // 이 값으로 비교해서 DTO 에 있는 조건 값을 true, false 를 설정해주면 된다.


        List<HotplaceDetailResponseDTO> list = hotplaceList.stream()
                .map(hotplace -> {
                    Member member = memberRepository.findByMemberAccount(hotplace.getMember().getMemberAccount());
                    return new HotplaceDetailResponseDTO(hotplace, member);
                })
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


        HotplaceDetailResponseDTO hotplaceDetailResponseDTO = new HotplaceDetailResponseDTO(saved, member);

        return HotplaceWriteResponseDTO.builder()
                .isWrite(hotplaceDetailResponseDTO.getHotplaceFullAddress() != null)
                .build();
    }

    private Member getAccount(String account) {
        return memberRepository.findByMemberAccount(account);
    }


    // 글 삭제
    public HotplaceDeleteResponseDTO delete(JwtUserInfo jwtUserInfo, Long hotplaceIdx) {
        Boolean isDelete = FALSE;
        Member member = getAccount(jwtUserInfo.getAccount());

        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException(hotplaceIdx + "의 글을 찾을 수 없습니다."));

        try {
            if (member.getMemberAccount().equals(hotplace.getMember().getMemberAccount())) {
                System.out.println("삭제 ================================================");
                hotplaceRepository.delete(hotplace);
                isDelete = TRUE;
            }
        } catch (NullPointerException e) {
            log.error("삭제에 실패하였습니다.");
            e.printStackTrace();
            throw new RuntimeException("삭제에 실패하였습니다.");
        }

        return HotplaceDeleteResponseDTO.builder()
                .isDelete(isDelete)
                .build();
    }


    // 글 수정
    public HotplaceDetailResponseDTO modify(
            JwtUserInfo jwtUserInfo
            , final HotplaceModifyRequestDTO dto
            , String uploadFilePath) {

        final Hotplace foundHotplace = findOne(dto.getHotplaceIdx());

//        Member member = getAccount(jwtUserInfo.getAccount());
        Member member = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        System.out.println("=============================================== 멤버" + member);

        if (member.getMemberAccount().equals(foundHotplace.getMember().getMemberAccount())) {
            dto.updateHotplace(member, foundHotplace, uploadFilePath);
            Hotplace modified = hotplaceRepository.saveAndFlush(foundHotplace);
            return new HotplaceDetailResponseDTO(modified);

//            HotplaceDetailResponseDTO responseDTO = new HotplaceDetailResponseDTO(modified, member);
//            System.out.println("responseDTO = " + responseDTO);

//            List<Hotplace> hotplaceList = hotplaceRepository.findByMember(member);
//            List<HotplaceDetailResponseDTO> list = hotplaceList.stream()
//                    .map(hotplace -> new HotplaceDetailResponseDTO(hotplace, member))
//                    .collect(Collectors.toList());
//
//            return HotplaceListResponseDTO.builder()
//                    .hotplaceList(list)
//                    .build();
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
                .map(hotplace -> new HotplaceDetailResponseDTO(hotplace, Member.builder().build()) )
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