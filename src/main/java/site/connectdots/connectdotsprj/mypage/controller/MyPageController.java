package site.connectdots.connectdotsprj.mypage.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;
import site.connectdots.connectdotsprj.mypage.dto.request.MemberInfoModifyRequestDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.*;
import site.connectdots.connectdotsprj.mypage.service.MyPageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

//    /**
//     * @param jwtUserInfo
//     * @return
//     */
//    @GetMapping()
//    public ResponseEntity<List<HotplaceDetailResponseDTO>> myPage(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
//        List<HotplaceDetailResponseDTO> member = myPageService.myPage(jwtUserInfo);
//        return ResponseEntity.ok(member);
//    }

    @GetMapping
    public ResponseEntity<?> myPageInfo(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        MyPageBasicDTO myPageInfo = myPageService.mypage(jwtUserInfo);

        return ResponseEntity.ok().body(myPageInfo);
    }

    /**
     * 내가 쓴 글 핫플레이스
     *
     * @param jwtUserInfo
     * @return
     */
    @GetMapping("/myactive/hotplace")
    public ResponseEntity<List<HotplaceDetailResponseDTO>> myActiveHotPlace(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        List<HotplaceDetailResponseDTO> member = myPageService.hotplace(jwtUserInfo);
        return ResponseEntity.ok(member);
    }

    /**
     * 내가 쓴 글 자유게시판
     *
     * @param jwtUserInfo
     * @return
     */
    @GetMapping("/myactive/freeboard")
    public ResponseEntity<List<FreeBoardResponseDTO>> myActiveFreeBoard(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        List<FreeBoardResponseDTO> dto = myPageService.myActiveFreeBoard(jwtUserInfo);
        return ResponseEntity.ok(dto);

    }

    /**
     * 내가 쓴 댓글 자유게시판
     *
     * @param jwtUserInfo
     * @return
     */
    @GetMapping("/myactive/freeboard/reply")
    public ResponseEntity<List<MyPageFreeBoardReplyResponseDTO>> myActiveFreeBoardReply(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        List<MyPageFreeBoardReplyResponseDTO> dto = myPageService.myActiveFreeBoardReply(jwtUserInfo);

        return ResponseEntity.ok(dto);
    }


    /**
     * 좋아요 핫플레이스
     *
     * @param jwtUserInfo
     * @return
     */
    @GetMapping("/like/hotplace")
    public ResponseEntity<List<HotplaceDetailResponseDTO>> likeHotPlace(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        List<HotplaceDetailResponseDTO> dto = myPageService.likeHotPlace(jwtUserInfo);
        return ResponseEntity.ok(dto);

    }

    /**
     * 좋아요 자유게시판
     *
     * @param jwtUserInfo
     * @return
     */
    @GetMapping("/myactive/freeboard/like")
    public ResponseEntity<List<MyPageFreeBoardResponseDTO>> likeFreeBoard(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        List<MyPageFreeBoardResponseDTO> dto = myPageService.likeFreeBoard(jwtUserInfo);

        return ResponseEntity.ok(dto);

    }

    //member/mypage/location/{memberIdx}
    //내위치 설정


    /**
     * 내 정보 수정  <Void> 따로 받아서 돌려줄게 없으니까 return 생략 가능
     *
     * @param jwtUserInfo
     * @param member
     * @return
     */
    @PutMapping("/modify")
    public ResponseEntity<Void> modifyMember(@AuthenticationPrincipal JwtUserInfo jwtUserInfo, @RequestBody MemberModifyRequestDTO member) {
        myPageService.modifyMember(jwtUserInfo, member);
        return ResponseEntity.ok().build();

    }

    /**
     * 회원삭제
     *
     * @param jwtUserInfo
     * @return
     */
    @DeleteMapping("/delete/{memberIdx}")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal JwtUserInfo jwtUserInfo) {
        myPageService.deleteMember(jwtUserInfo);
        return ResponseEntity.ok().build();
    }


    // 프로필 사진 등록
    @PostMapping("/profile")
    public ResponseEntity<?> uploadProfile(
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo
            , @RequestPart(value = "profileImage") MultipartFile memberProfile) throws IOException {

        try {
            if (memberProfile != null) {
                log.info("프로필 이미지 파일명 : {} ====================", memberProfile.getOriginalFilename());
                String uploadFilePath = myPageService.uploadProfile(jwtUserInfo, memberProfile);


                return ResponseEntity.ok().build();
            }
        } catch (IOException e) {
            log.warn("파일업로드 예외 발생");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

        return null;
    }


    // 이미지 응답 처리
    @GetMapping("/load-s3")
    public ResponseEntity<?> loadS3(
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo) {

        log.info("/member/mypage/load-s3 GET =================={}", jwtUserInfo);

        try {
            String profilePath = myPageService.getProfilePath(jwtUserInfo.getAccount());
            return ResponseEntity.ok().body(profilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //     회원 정보 수정
    @PatchMapping("/modify")
    public ResponseEntity<?> modifyMemberInfo(
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo
            , @RequestBody MemberInfoModifyRequestDTO requestDTO) {

        System.out.println("===================================");
        System.out.println(requestDTO);
        System.out.println("===================================");
        myPageService.modifyMemberInfo(jwtUserInfo, requestDTO);

        return ResponseEntity.ok().build();
    }


}
