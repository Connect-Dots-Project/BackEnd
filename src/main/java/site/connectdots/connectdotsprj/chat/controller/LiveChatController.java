package site.connectdots.connectdotsprj.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.chat.dto.request.LivechatCreateRequestDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatCreateResponseDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatListAndHashtagListResponseDTO;
import site.connectdots.connectdotsprj.chat.entity.Livechat;
import site.connectdots.connectdotsprj.chat.service.LivechatService;
import site.connectdots.connectdotsprj.jwt.config.JwtUserInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/chat")
public class LiveChatController {
    private final LivechatService livechatService;

    // 맨 처음 페이지 들어갈 때 전체 조회
    @GetMapping()
    public ResponseEntity<LivechatListAndHashtagListResponseDTO> findAllLivechat() {
        LivechatListAndHashtagListResponseDTO listResponseDTO = livechatService.findAllLivechat();

        return ResponseEntity.ok().body(listResponseDTO);
    }

    // 해시태그로 게시물 조회하기
    @GetMapping("/hashtag")
    public ResponseEntity<LivechatListAndHashtagListResponseDTO> findAllLivechatByHashtag(@RequestParam(name = "value") String hashtag) {
        LivechatListAndHashtagListResponseDTO hashtagListDTO = livechatService.findAllLivechatByHashtag(hashtag);

        return ResponseEntity.ok().body(hashtagListDTO);
    }

    // 글 작성
    @PostMapping()
    public ResponseEntity<LivechatCreateResponseDTO> createLivechat(
            @RequestBody LivechatCreateRequestDTO dto,
            @AuthenticationPrincipal JwtUserInfo userInfo
    ) {

        System.out.println("\n\n\n----------------------66666----------------------");
        System.out.println(userInfo);
        System.out.println(dto);
        System.out.println("----------------------66666----------------------\n\n\n");

        if (userInfo == null) {
            ResponseEntity.ok().body("test fail");
        }
        LivechatCreateResponseDTO livechat = livechatService.createLivechat(dto, userInfo);

        return ResponseEntity.ok().body(livechat);
    }

}
