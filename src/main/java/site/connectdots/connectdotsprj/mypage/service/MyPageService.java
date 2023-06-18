package site.connectdots.connectdotsprj.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageWriteDTO;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final HotplaceRepository hotplaceRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardReplyRepository freeBoardReplyRepository;


    @Transactional(readOnly = true)
    public MyPageWriteDTO findById(Long memberIdx) {
        Member member = memberRepository.findByMemberIdx(memberIdx);
        if (member == null) {
            // 멤버를 찾지 못한 경우에 대한 예외 처리
            throw new NotFoundException("Member not found");
        }
        return new MyPageWriteDTO(member);
    }
}
