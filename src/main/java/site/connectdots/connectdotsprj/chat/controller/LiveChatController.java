package site.connectdots.connectdotsprj.chat.controller;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.connectdots.connectdotsprj.chat.dto.request.LiveChatSenderRequestDTO;
import site.connectdots.connectdotsprj.chat.dto.request.LivechatCreateRequestDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LiveChatSenderResponseDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatCreateResponseDTO;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatListAndHashtagListResponseDTO;
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
        LivechatCreateResponseDTO livechat = livechatService.createLivechat(dto, userInfo);

        return ResponseEntity.ok().body(livechat);
    }


    @PostMapping("/check-sender")
    public ResponseEntity<?> setupMessages(
            @AuthenticationPrincipal JwtUserInfo jwtUserInfo,
            @RequestBody LiveChatSenderRequestDTO dto) {

        System.out.println("\n\n\n\n\n\n\n====================================");
        System.out.println(jwtUserInfo);
        System.out.println(dto);
        System.out.println("====================================\n\n\n\n\n\n");


        LiveChatSenderResponseDTO liveChatSenderResponseDTO = livechatService.setupMessages(jwtUserInfo, dto);

        return ResponseEntity.ok().body(liveChatSenderResponseDTO);
    }


    /**
     * 로그아웃 or 브라우저 종료시 삭제한다.
     *
     * @param userInfo
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<?> deleteLivechat(@AuthenticationPrincipal JwtUserInfo userInfo) {
        livechatService.deleteLivechat(userInfo);
        return ResponseEntity.ok().body("");
    }

}
