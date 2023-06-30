package site.connectdots.connectdotsprj.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.aws.service.S3Service;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardLike;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardLikeRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardReplyRepository;
import site.connectdots.connectdotsprj.freeboard.repository.FreeBoardRepository;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.repository.HotplaceRepository;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.member.repository.MemberRepository;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberModifyRequestDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageBasicDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageFreeBoardReplyResponseDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageFreeBoardResponseDTO;
import site.connectdots.connectdotsprj.mypage.entity.Like;
import site.connectdots.connectdotsprj.mypage.entity.LikeType;
import site.connectdots.connectdotsprj.mypage.repository.LikeRepository;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private final FreeBoardLikeRepository freeBoardLikeRepository;
    private final S3Service s3Service;

    private List<HotplaceDetailResponseDTO> makeHotplaceDetailResponseDTOList(String account) {
        Member member = memberRepository.findByMemberAccount(account);

        List<Hotplace> hotplaceList = hotplaceRepository.findByMember(member);
        List<HotplaceDetailResponseDTO> response = hotplaceList.stream().map(HotplaceDetailResponseDTO::new).collect(Collectors.toList());
        return response;
    }

    public List<HotplaceDetailResponseDTO> hotplace(JwtUserInfo jwtUserInfo) {
        return makeHotplaceDetailResponseDTOList(jwtUserInfo.getAccount());
    }

    public MyPageBasicDTO mypage(JwtUserInfo jwtUserInfo) {
        Member foundMember = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        return new MyPageBasicDTO(foundMember);
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

    public List<MyPageFreeBoardResponseDTO> likeFreeBoard(JwtUserInfo jwtUserInfo) {
        List<FreeBoardLike> likeList = freeBoardLikeRepository.findByMemberAccount(jwtUserInfo.getAccount());

        return likeList.stream().map(freeBoard ->
                        freeBoardRepository
                                .findById(freeBoard.getFreeboardLikeIdx())
                                .orElseThrow())
                .map(MyPageFreeBoardResponseDTO::new)
                .collect(Collectors.toList());
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
                        .freeBoardWriteDate(freeboard.getFreeBoardWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build())
                .collect(Collectors.toList());
    }

    //댓글의 경우 게시글 + 댓글content까지 -> 그러면 따로 dto를 다시 만들어야할까
    public List<MyPageFreeBoardReplyResponseDTO> myActiveFreeBoardReply(JwtUserInfo jwtUserInfo) {
        Member foundMember = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());

//        freeBoardReplyRepository.findAllByMemberMemberIdx(foundMember);
        List<FreeBoardReply> replyList = freeBoardReplyRepository.findByMember(foundMember);
        List<MyPageFreeBoardReplyResponseDTO> responseList = new ArrayList<>();

        for (FreeBoardReply freeBoardReply : replyList) {
            Long freeBoardIdx = freeBoardReply.getFreeBoard().getFreeBoardIdx();
            FreeBoard freeBoard = freeBoardRepository.findById(freeBoardIdx).orElseThrow();
            responseList.add(new MyPageFreeBoardReplyResponseDTO(freeBoard, freeBoardReply));
        }


        return responseList;
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



    public String uploadProfile(JwtUserInfo jwtUserInfo, MultipartFile memberProfile) throws IOException {

        String uniqueFileName = UUID.randomUUID() + "-" + memberProfile.getOriginalFilename();
        String uploadURL = s3Service.uploadToS3Bucket(memberProfile.getBytes(), uniqueFileName);
        System.out.println(uploadURL);

        Member foundMember = memberRepository.findByMemberAccount(jwtUserInfo.getAccount());
        foundMember.setMemberProfile(uploadURL);
        memberRepository.save(foundMember);

        return uploadURL;
    }


    // 이미지 클라이언트에게 응답

    public String getProfilePath(String account) {
        Member member = memberRepository.findByMemberAccount(account);
        return member.getMemberProfile();
    }

}
