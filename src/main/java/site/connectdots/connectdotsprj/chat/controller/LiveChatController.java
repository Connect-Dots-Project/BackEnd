package site.connectdots.connectdotsprj.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatListAndHashtagListResponseDTO;
import site.connectdots.connectdotsprj.chat.entity.Livechat;
import site.connectdots.connectdotsprj.chat.service.LivechatService;

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
    public ResponseEntity<?> createLivechat() {
        Livechat livechat = livechatService.createLivechat();

        return ResponseEntity.ok().body(livechat);
    }

}
