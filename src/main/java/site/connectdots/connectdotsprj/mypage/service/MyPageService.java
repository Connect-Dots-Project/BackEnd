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
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;
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

    private List<HotplaceDetailResponseDTO> makeHotplaceDetailResponseDTOList(String account) {
        Member member = memberRepository.findByMemberAccount(account);

        List<Hotplace> hotplaceList = hotplaceRepository.findByMember(member);
        List<HotplaceDetailResponseDTO> response = hotplaceList.stream().map(HotplaceDetailResponseDTO::new).collect(Collectors.toList());
        return response;
    }

    public List<HotplaceDetailResponseDTO> myPage(JwtUserInfo jwtUserInfo) {
       return makeHotplaceDetailResponseDTOList(jwtUserInfo.getAccount());
    }

    public List<HotplaceDetailResponseDTO> likeHotPlace(JwtUserInfo jwtUserInfo) {
        Member member = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        List<Like> likeList = likeRepository.findByMemberAndType(member, LikeType.HOTPLACE);

        List<Long> hotplaceIdxList = likeList.stream().map(Like::getItemIdx).collect(Collectors.toList());

        List<Hotplace> hotplaceList = hotplaceRepository.findByHotplaceIdxIn(hotplaceIdxList);

        List<HotplaceDetailResponseDTO> response = hotplaceList.stream().map(hotplace -> HotplaceDetailResponseDTO.builder()
                        .hotplaceImg(hotplace.getHotplaceImg())
                        .hotplaceContent(hotplace.getHotplaceContent())
                        .hotplaceFullAddress(hotplace.getHotplaceFullAddress()).build())
                .collect(Collectors.toList());
        return response;
    }

    public List<FreeBoardResponseDTO> likeFreeBoard(JwtUserInfo jwtUserInfo) {

        Member member = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        List<Like> likeList = likeRepository.findByMemberAndType(member, LikeType.FREEBOARD);

        List<Long> freeboardIdxList = likeList.stream().map(Like::getItemIdx).collect(Collectors.toList());

        List<FreeBoard> freeboardList = freeBoardRepository.findByFreeBoardIdxIn(freeboardIdxList);

        List<FreeBoardResponseDTO> response = freeboardList.stream().map(freeboard -> FreeBoardResponseDTO.builder()
                        .freeBoardContent(freeboard.getFreeBoardContent())
                        .freeBoardCategory(freeboard.getFreeBoardCategory().name())
                        .freeBoardTitle(freeboard.getFreeBoardTitle()).build())
                .collect(Collectors.toList());
        return response;


    }
    private List<FreeBoard> getFreeBoardListByMember(JwtUserInfo jwtUserInfo) {
        Member member = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        return freeBoardRepository.findByMember(member);
    }

    public List<FreeBoardResponseDTO> myActiveFreeBoard(JwtUserInfo jwtUserInfo) {
        List<FreeBoard> freeboardList = getFreeBoardListByMember(jwtUserInfo);

       return freeboardList.stream().map(freeboard -> FreeBoardResponseDTO.builder()
                        .freeBoardCategory(freeboard.getFreeBoardCategory().name())
                        .freeBoardLocation(freeboard.getFreeBoardLocation())
                        .freeBoardTitle(freeboard.getFreeBoardTitle())
                        .freeBoardWriteDate(String.valueOf(freeboard.getFreeBoardWriteDate())).build())
                .collect(Collectors.toList());
    }

    //댓글의 경우 게시글 + 댓글content까지 -> 그러면 따로 dto를 다시 만들어야할까
    public List<FreeBoardDetailReplyDTO> myActiveFreeBoardReply(JwtUserInfo jwtUserInfo) {
        List<FreeBoard> freeboardList = getFreeBoardListByMember(jwtUserInfo);

       return freeboardList.stream().map(freeBoardReply -> FreeBoardDetailReplyDTO.builder()
                .freeBoardReplyIdx(freeBoardReply.getFreeBoardIdx())
                .freeBoardReplyContent(freeBoardReply.getFreeBoardContent())
                .build()).collect(Collectors.toList());
    }

    public void modifyMember(JwtUserInfo jwtUserInfo, MemberModifyRequestDTO modifyRequestDTO) {
        Member member = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        member.setMemberPassword(modifyRequestDTO.getFirstPassword());
        member.setMemberName(modifyRequestDTO.getName());
        member.setMemberNickname(modifyRequestDTO.getNickName());
        member.setMemberPhone(modifyRequestDTO.getPhone());
        member.setMemberLocation(modifyRequestDTO.getLocation());
        member.setMemberComment(modifyRequestDTO.getComment());

        memberRepository.save(member);
    }


    public void deleteMember(JwtUserInfo jwtUserInfo) {

        Member member = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        member.setMemberIdx(member.getMemberIdx());
        member.setMemberAccount(member.getMemberAccount());

        memberRepository.delete(member);

    }


}
