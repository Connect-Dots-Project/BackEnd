package site.connectdots.connectdotsprj.mypage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.mypage.dto.response.MemberDetailDTO;
import site.connectdots.connectdotsprj.mypage.dto.response.MyPageWriteDTO;
import site.connectdots.connectdotsprj.mypage.service.MyPageService;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 마이페이지 - 내가 작성한 글/댓글
    @GetMapping("/mypage/{memberIdx}")
    public ResponseEntity<MyPageWriteDTO> findByIdx(@PathVariable Long memberIdx) {
        MyPageWriteDTO myPageWriteDTO = myPageService.findById(memberIdx);
        return ResponseEntity.ok(myPageWriteDTO);
    }
    //회원 상세조회 - findByIdx(Long memberIdx)
    //회원삭제 - @Delete deleteByIdx
    //회원수정 get, post 둘다

    //내가 쓴 글 /GET: /member/mypage/{memberIdx}
    //내가 좋아요 누른 글, 댓글 GET: /member/commets/board & like {memberIdx}
    //좋아요 편의점 GET: /member/convenientstore/{memberIdx}
    //장소 GET: /member/location/{memberIdx}
    //팔로잉 팔로우

}
