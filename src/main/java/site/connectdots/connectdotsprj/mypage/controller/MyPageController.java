package site.connectdots.connectdotsprj.mypage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberModifyRequestDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageBasicDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageFreeBoardReplyResponseDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageFreeBoardResponseDTO;
import site.connectdots.connectdotsprj.mypage.service.MyPageService;

import java.util.List;

@RestController
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
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


}
