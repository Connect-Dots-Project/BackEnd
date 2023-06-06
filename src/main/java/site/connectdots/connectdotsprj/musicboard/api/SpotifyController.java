package site.connectdots.connectdotsprj.musicboard.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import site.connectdots.connectdotsprj.musicboard.dto.response.SpotifyTokenDTO;
import site.connectdots.connectdotsprj.musicboard.service.SpotifyAuthService;
import site.connectdots.connectdotsprj.musicboard.dto.response.SpotifyPlaylistDTO;
import site.connectdots.connectdotsprj.musicboard.service.SpotifyPlaylistService;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contents/spotify")
public class SpotifyController {

    private final SpotifyAuthService spotifyAuthService;
    private final SpotifyPlaylistService spotifyPlaylistService;

    private RestTemplate restTemplate;

    @Autowired
    public SpotifyController(SpotifyAuthService spotifyAuthService, SpotifyPlaylistService spotifyPlaylistService) {
        this.spotifyAuthService = spotifyAuthService;
        this.spotifyPlaylistService = spotifyPlaylistService;
    }

    // 로그인 페이지 이동 로직 작성
    @GetMapping("/login")
    public String login() {
        String authorizationUrl = spotifyAuthService.getAuthorizationUrl();
        return "redirect:" + authorizationUrl;
    }

    // 콜백 처리 로직 작성
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        SpotifyTokenDTO token = spotifyAuthService.requestAccessToken(code);
        System.out.println("Access Token: " + token.getAccessToken());

        return "redirect:/success"; // 로그인 성공 시 이동할 페이지의 URL
    }

    //  토큰 갱신 로직 작성
    @GetMapping("/refresh-token")
    public String refreshToken(@RequestParam("refreshToken") String refreshToken) {
        SpotifyTokenDTO refreshedToken = spotifyAuthService.refreshAccessToken(refreshToken);
        // 갱신된 액세스 토큰을 활용하여 필요한 작업 수행

        return "redirect:/refresh-success"; // 토큰 갱신 성공 시 이동할 페이지의 URL
    }

    // 재생 목록 가져오기
    @GetMapping("/playlist")
    public ResponseEntity<?> getPlaylist() {
        // API 엔드포인트 URL
        String url = "https://api.spotify.com/v1/playlists/{playlist_id}";

        try {
            // API 호출
            ResponseEntity<SpotifyPlaylistDTO> response = restTemplate.getForEntity(url, SpotifyPlaylistDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                // API 호출이 실패한 경우
                return ResponseEntity.status(response.getStatusCode()).body("API 호출 실패");
            }
        } catch (RestClientException e) {
            // RestTemplate 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API 호출 예외 발생");
        }
    }

}






























//@RestController
//public class SpotifyController {
//
//    private final SpotifyAuthService spotifyAuthService;
//
//    @Autowired
//    public SpotifyController(SpotifyAuthService spotifyAuthService) {
//        this.spotifyAuthService = spotifyAuthService;
//    }
//
//    // 로그인 페이지 이동 로직 작성
//    @GetMapping("/login")
//    public String login() {
//        String authorizationUrl = spotifyAuthService.getAuthorizationUrl();
//        return "redirect:" + authorizationUrl;
//    }
//
//    // 콜백 처리 로직 작성
//    @GetMapping("/callback")
//    public String callback(@RequestParam("code") String code) {
//        SpotifyTokenDTO token = spotifyAuthService.requestAccessToken(code);
//        System.out.println("Access Token: " + token.getAccessToken());
//
//        return "redirect:/success"; // 로그인 성공 시 이동할 페이지의 URL
//    }
//
//    // 리프레시 토큰 갱신 로직 작성
//    @GetMapping("/refresh-token")
//    public String refreshToken(@RequestParam("refreshToken") String refreshToken) {
//        SpotifyTokenDTO refreshedToken = spotifyAuthService.refreshAccessToken(refreshToken);
//        // 갱신된 액세스 토큰을 활용하여 필요한 작업 수행
//
//        return "redirect:/refresh-success"; // 토큰 갱신 성공 시 이동할 페이지의 URL
//    }
//}
