package site.connectdots.connectdotsprj.mypage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailReplyDTO;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardResponseDTO;
import site.connectdots.connectdotsprj.hotplace.dto.responseDTO.HotplaceDetailResponseDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberModifyRequestDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageBasicDTO;
import site.connectdots.connectdotsprj.mypage.service.MyPageService;

import java.util.List;

@RestController
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 마이페이지
     * @param memberIdx
     * @return
     */
    @GetMapping("/{memberIdx}")
    public ResponseEntity<Void> myPage(@PathVariable Long memberIdx){
        myPageService.myPage(memberIdx);
        return ResponseEntity.ok().build();
    }
    /**
     * 내가 쓴 글 핫플레이스
     * @param memberIdx
     * @return
     */
    @GetMapping("/myactive/hotplace/{memberIdx}")
    public ResponseEntity<List<HotplaceDetailResponseDTO>> myActiveHotPlace(@PathVariable Long memberIdx){
        List<HotplaceDetailResponseDTO> dto = myPageService.myActiveHotPlace(memberIdx);
        return ResponseEntity.ok(dto);

    }

    /**
     * 내가 쓴 글 자유게시판
     * @param memberIdx
     * @return
     */

    @GetMapping("/myactive/freeboard/{memberIdx}")
    public ResponseEntity<List<FreeBoardResponseDTO>> myActiveFreeBoard(@PathVariable Long memberIdx){
        List<FreeBoardResponseDTO> dto = myPageService.myActiveFreeBoard(memberIdx);
        return ResponseEntity.ok(dto);

    }

    /**
     * 내가 쓴 댓글 자유게시판
     * @param memberIdx
     * @return
     */
    //자유게시판 내가 쓴 댓글
    @GetMapping("/myactive/freeboard/reply/{memberIdx}")
    public ResponseEntity<List<FreeBoardDetailReplyDTO>> myActiveFreeBoardReply(@PathVariable Long memberIdx){
        List<FreeBoardDetailReplyDTO> dto = myPageService.myActiveFreeBoardReply(memberIdx);
        return ResponseEntity.ok(dto);
    }


    /**
     * 좋아요 핫플레이스
     * @param memberIdx
     * @return
     */
@GetMapping("/like/hotplace/{memberIdx}")
    public ResponseEntity<List<HotplaceDetailResponseDTO>> likeHotPlace(@PathVariable Long memberIdx){
    List<HotplaceDetailResponseDTO> dto = myPageService.likeHotPlace(memberIdx);
    return ResponseEntity.ok(dto);

}

    /**
     * 좋아요 자유게시판
     * @param memberIdx
     * @return
     */
    @GetMapping("/like/freeboard/{memberIdx}")
    public ResponseEntity<List<FreeBoardResponseDTO>> likeFreeBoard(@PathVariable Long memberIdx){
        List<FreeBoardResponseDTO> dto = myPageService.likeFreeBoard(memberIdx);
        return ResponseEntity.ok(dto);

    }

    //member/mypage/location/{memberIdx}
    //내위치 설정



    /**
     * 내 정보 수정  <Void> 따로 받아서 돌려줄게 없으니까 return 생략 가능
     * @param memberIdx
     * @param member
     * @return
     */
    @PutMapping("/modify/{memberIdx}")
    public ResponseEntity<Void> modifyMember(@PathVariable Long memberIdx, @RequestBody MemberModifyRequestDTO member){
        myPageService.modifyMember(memberIdx, member);
        return ResponseEntity.ok().build();

    }

     /**
     * 회원삭제
     * @param memberIdx
     * @return
     */
    @DeleteMapping("/delete/{memberIdx}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberIdx){
        myPageService.deleteMember(memberIdx);
        return ResponseEntity.ok().build();
    }


}
