package site.connectdots.connectdotsprj.hotplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceWriteRequestDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HotplaceService {

    private final HotplaceRepository hotplaceRepository;
    private final MemberRepository memberRepository;

    // 글 전체조회
    public List<Hotplace> findAll() {
        return hotplaceRepository.findAll();
    }

    // 글 작성
    public HotplaceResponseDTO write(final HotplaceWriteRequestDTO dto)throws RuntimeException {

        Member member = memberRepository.findById(dto.getMemberIdx())
                        .orElseThrow();

        Hotplace hotplace = dto.toEntity();
        hotplace.setMember(member);

        log.info("hotplaceService.write.info {}", member);

        Hotplace save = hotplaceRepository.save(hotplace);

        return new HotplaceResponseDTO(save);
    }

    // 글 삭제
    public void delete(Long hotplaceIdx) {
        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException("해당 hotplaceIdx의 글을 찾을 수 없습니다."));

        hotplaceRepository.delete(hotplace);
    }


    // 글 수정
    public Hotplace modify(Long hotplaceIdx, HotplaceModifyRequestDTO dto) {
        // 조회
        Hotplace hotplace = hotplaceRepository.findById(hotplaceIdx)
                .orElseThrow(() -> new RuntimeException("해당 hotplaceIdx의 글을 찾을 수 없습니다."));

        //세터
        dto.updateHotplace(hotplace);

        // 저장
        return hotplaceRepository.save(hotplace);
    }



}
