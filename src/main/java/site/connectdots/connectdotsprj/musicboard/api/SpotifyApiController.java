package site.connectdots.connectdotsprj.musicboard.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.connectdots.connectdotsprj.musicboard.dto.response.TrackBoardListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.service.SpotifyApiService;

import java.util.List;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SpotifyApiController {

    private final SpotifyApiService spotifyApiService;

    /**
     * 로그인
     *
     * @return
     */
    @GetMapping("/spotify-login")
    public ModelAndView spotifyLogin() {
        String s = spotifyApiService.spotifyLogin();
        return new ModelAndView(s);
    }

    /**
     * 플레이리스트 안의 음악 DB 추가
     *
     * @param code
     * @return
     */
    @PostMapping("/admin/contents/music-board")
    public HttpEntity<Void> updateMusicBoard(String code) {
        spotifyApiService.updateMusicBoard(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 플레이리스트 전체 가져오기
     *
     * @return
     */
    @GetMapping("/contents/music-board")
    public HttpEntity<List<TrackBoardListResponseDTO>> getMusicBoardList() {
        List<TrackBoardListResponseDTO> response = spotifyApiService.getMusicBoardList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/contents/music-board/{playListId}")
    public HttpEntity<List<MusicListResponseDTO>> getMusicList(@PathVariable long playListId) {
        List<MusicListResponseDTO> response = spotifyApiService.getMusicList(playListId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


