package site.connectdots.connectdotsprj.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberModifyRequestDTO;
import site.connectdots.connectdotsprj.mypage.entity.Like;
import site.connectdots.connectdotsprj.mypage.entity.LikeType;
import site.connectdots.connectdotsprj.mypage.repository.LikeRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;
    private final HotplaceRepository hotplaceRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardReplyRepository freeBoardReplyRepository;
    private final LikeRepository likeRepository;


     public Member myPage(Long memberIdx) {
         Member member = memberRepository.findByMemberIdx(memberIdx);

         return member;
         }



    public List<HotplaceDetailResponseDTO> likeHotPlace(Long memberIdx) {
        Member member = memberRepository.findByMemberIdx(memberIdx);
        List<Like> likeList = likeRepository.findByMemberAndType(member, LikeType.HOTPLACE);

        List<Long> hotplaceIdxList = likeList.stream().map(Like::getItemIdx).collect(Collectors.toList());

        List<Hotplace> hotplaceList = hotplaceRepository.findByHotplaceIdxIn(hotplaceIdxList);

       List<HotplaceDetailResponseDTO> response =  hotplaceList.stream().map(hotplace -> HotplaceDetailResponseDTO.builder()
                .hotplaceImg(hotplace.getHotplaceImg())
                .hotplaceContent(hotplace.getHotplaceContent())
                .hotplaceFullAddress(hotplace.getHotplaceFullAddress()).build())
                .collect(Collectors.toList());
        return response;
    }

    public List<FreeBoardResponseDTO> likeFreeBoard(Long memberIdx) {

        Member member = memberRepository.findByMemberIdx(memberIdx);
        List<Like> likeList = likeRepository.findByMemberAndType(member, LikeType.FREEBOARD);

        List<Long> freeboardIdxList = likeList.stream().map(Like::getItemIdx).collect(Collectors.toList());

        List<FreeBoard> freeboardList = freeBoardRepository.findByFreeBoardIdxIn(freeboardIdxList);

        List<FreeBoardResponseDTO> response =  freeboardList.stream().map(freeboard -> FreeBoardResponseDTO.builder()
                .freeBoardContent(freeboard.getFreeBoardContent())
                .freeBoardCategory(freeboard.getFreeBoardCategory().name())
                .freeBoardTitle(freeboard.getFreeBoardTitle()).build())
                .collect(Collectors.toList());
        return response;


    }

    public List<HotplaceDetailResponseDTO> myActiveHotPlace(Long memberIdx) {

        Member member = memberRepository.findByMemberIdx(memberIdx);

        List<Hotplace> hotplaceList = hotplaceRepository.findByMember(member);


        List<HotplaceDetailResponseDTO> response =  hotplaceList.stream().map(hotplace -> HotplaceDetailResponseDTO.builder()
                        .hotplaceImg(hotplace.getHotplaceImg())
                        .hotplaceContent(hotplace.getHotplaceContent())
                        .hotplaceFullAddress(hotplace.getHotplaceFullAddress()).build())
                .collect(Collectors.toList());
        return response;
    }

    public List<FreeBoardResponseDTO> myActiveFreeBoard(Long memberIdx) {
        Member member = memberRepository.findByMemberIdx(memberIdx);
        List<FreeBoard> freeboardList = freeBoardRepository.findByMember(member);

        List<FreeBoardResponseDTO> response =  freeboardList.stream().map(freeboard -> FreeBoardResponseDTO.builder()
                        .freeBoardCategory(freeboard.getFreeBoardCategory().name())
                        .freeBoardLocation(freeboard.getFreeBoardLocation())
                        .freeBoardTitle(freeboard.getFreeBoardTitle())
                        .freeBoardWriteDate(String.valueOf(freeboard.getFreeBoardWriteDate())).build())
                .collect(Collectors.toList());
        return response;
    }

    //댓글의 경우 게시글 + 댓글content까지 -> 그러면 따로 dto를 다시 만들어야할까
    public List<FreeBoardDetailReplyDTO> myActiveFreeBoardReply(Long memberIdx) {

        Member member = memberRepository.findByMemberIdx(memberIdx);
        List<FreeBoard> freeboardList = freeBoardRepository.findByMember(member);
        List<FreeBoardDetailReplyDTO> response = freeboardList.stream().map(freeBoardReply -> FreeBoardDetailReplyDTO.builder()
                .freeBoardReplyIdx(freeBoardReply.getFreeBoardIdx())
                .freeBoardReplyContent(freeBoardReply.getFreeBoardContent())
                .build()).collect(Collectors.toList());
        return response;

    }
    public void modifyMember(Long memberIdx, MemberModifyRequestDTO modifyRequestDTO) {
        Member member = memberRepository.findByMemberIdx(memberIdx);
        member.setMemberPassword(modifyRequestDTO.getFirstPassword());
        member.setMemberName(modifyRequestDTO.getName());
        member.setMemberNickname(modifyRequestDTO.getNickName());
        member.setMemberPhone(modifyRequestDTO.getPhone());
        member.setMemberLocation(modifyRequestDTO.getLocation());
        member.setMemberComment(modifyRequestDTO.getComment());

        memberRepository.save(member);
    }


    public void deleteMember(Long memberIdx) {

        Member member = memberRepository.findByMemberIdx(memberIdx);
        member.setMemberIdx(member.getMemberIdx());
        member.setMemberAccount(member.getMemberAccount());

        memberRepository.delete(member);

    }


}
