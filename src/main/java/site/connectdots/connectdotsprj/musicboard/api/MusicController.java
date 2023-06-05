package site.connectdots.connectdotsprj.musicboard.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.service.MusicService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contents/music-board")
public class MusicController {
    private final MusicService musicservice;

    @GetMapping
    public ResponseEntity<?> musiclist(MusicListResponseDTO musicListResponseDTO){
        log.info("{}{}", musicListResponseDTO.getMusic_board_title());

        MusicListResponseDTO dto = musicservice.findAll(musicListResponseDTO);
        return ResponseEntity.ok().body(dto);
    }


}
