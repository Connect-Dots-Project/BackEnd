package site.connectdots.connectdotsprj.musicboard.api;


import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import site.connectdots.connectdotsprj.musicboard.entity.Music;
import site.connectdots.connectdotsprj.musicboard.repository.MusicRepository;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;
import java.net.URI;
import java.util.*;
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.michaelthelin.spotify.SpotifyApi;
import site.connectdots.connectdotsprj.musicboard.dto.response.TrackBoardListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.service.SpotifyApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> b4b5e409c0b6a1d7557221fdba145a9c21e6da79


@RestController
@CrossOrigin
@RequiredArgsConstructor
<<<<<<< HEAD
public class SpotifyApiController {

    private final MusicRepository musicRepository;
    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8181/contents/music-board");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();


    @GetMapping("/spotify-login")
    public ModelAndView spotifyLogin() {
        String s = "redirect:https://accounts.spotify.com/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;

        return new ModelAndView(s);
    }

    @GetMapping("/contents/music-board")
    public void TrackList(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());


        Map<Long, String> playlistIds = new HashMap<>();
        playlistIds.put(1L, "37i9dQZF1DWT9uTRZAYj0c");
        playlistIds.put(2L, "37i9dQZEVXbMDoHDwVN2tF");
        playlistIds.put(3L, "37i9dQZF1DWWwaxRea1LWS");
        playlistIds.put(4L, "5wBovveXvw0woKL72NqA0P");
        playlistIds.put(5L, "6OlqhvuJE4cdphMx610BtM");
        playlistIds.put(6L, "37i9dQZF1DWTY99d0AYptp");
        playlistIds.put(7L, "37i9dQZF1EIUCUQCE4UP0F");
        playlistIds.put(8L, "37i9dQZF1DXbSWYCNwaARB");
        playlistIds.put(9L, "37i9dQZF1DWVuUd3Ffrcx8");
        playlistIds.put(10L, "37i9dQZF1EVHGWrwldPRtj");

// 노래 트랙, 이미지 사진 가져오기
        for (Map.Entry<Long, String> entry : playlistIds.entrySet()) {
            final Playlist playlist = spotifyApi.getPlaylist(entry.getValue()).build().execute();
            String name = playlist.getName();
            Image[] images = playlist.getImages();
            System.out.println(name);
            for (Image image : images) {
                String url = image.getUrl();
                System.out.println("url = " + url);
            }

            System.out.println();
            System.out.println();
        }

    }

    @GetMapping("/contents/music-board/{musicBoardIdx}")
    public void MusicList(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {
        Map<Long, String> playlistIds = new HashMap<>();
        playlistIds.put(1L, "37i9dQZF1DWT9uTRZAYj0c");
        playlistIds.put(2L, "37i9dQZEVXbMDoHDwVN2tF");
        playlistIds.put(3L, "37i9dQZF1DWWwaxRea1LWS");
        playlistIds.put(4L, "5wBovveXvw0woKL72NqA0P");
        playlistIds.put(5L, "6OlqhvuJE4cdphMx610BtM");
        playlistIds.put(6L, "37i9dQZF1DWTY99d0AYptp");
        playlistIds.put(7L, "37i9dQZF1EIUCUQCE4UP0F");
        playlistIds.put(8L, "0WOCvglRdeDmMnt8zFV0vc");
        playlistIds.put(9L, "37i9dQZF1DWVuUd3Ffrcx8");
        playlistIds.put(10L, "37i9dQZF1EVHGWrwldPRtj");

        //트랙 클릭시 노래리스트 가져오기 (노래제목, 앨범이미지, 가수, 미리듣기)
        for (Map.Entry<Long, String> entry : playlistIds.entrySet()) {
            Music music = new Music();

            final Playlist playlist = spotifyApi.getPlaylist(entry.getValue()).build().execute();
            String name = playlist.getName();
            Image[] images = playlist.getImages();
            System.out.println(name);

            music.setMusicBoardTrack(name);
            music.setMusicBoardTrackImg(images[0].getUrl());

            // 해당 플레이리스트의 트랙 노래 정보 가져오기
            Paging<PlaylistTrack> playlistTracksInfo = playlist.getTracks();
            PlaylistTrack[] playlistTracksArray = playlistTracksInfo.getItems();
            for (PlaylistTrack playlistTrack : playlistTracksArray) {
                IPlaylistItem track = playlistTrack.getTrack();
                String info = track.toString();

                List<String> information = new ArrayList<>();
                String[] split = info.split(",");
                boolean[] visited = new boolean[3];


                for (String s : split) {

                    if (!visited[0] && s.trim().contains("artists=[ArtistSimplified(name=")) {
                        information.add(s.trim());
                        visited[0] = true;
                    }

                    if (!visited[1] && s.trim().contains("url=")) {
                        information.add(s.trim());
                        visited[1] = true;
                    }

                    if (!visited[2] && s.trim().contains("previewUrl=")) {
                        information.add(s.trim());
                        visited[2] = true;
                    }
                }

                System.out.println("Track Name: " + track.getName());
                System.out.println(information.get(0).replaceAll("artists=\\[ArtistSimplified\\(name=", "").trim());
                System.out.println(information.get(1).replaceAll("url=", "").trim());
                System.out.println(information.get(2).replaceAll("previewUrl=", "").trim());


            }
        }
    }
=======
@Slf4j
public class SpotifyApiController {

    @Autowired
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
    public HttpEntity<?> updateMusicBoard(String code) {
        String token = spotifyApiService.updateMusicBoard(code);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

//    @PostMapping("/admin/contents/music-board")
//    public ResponseEntity<Map<String, String>> updateMusicBoard(@RequestBody Map<String, String> requestBody) {
//        String code = requestBody.get("code");
//        String token = spotifyApiService.updateMusicBoard(code);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//
//        return ResponseEntity.ok(response);
//    }


    /**
     * 플레이리스트 전체 가져오기
     *
     * @return
     */
    @GetMapping("/contents/music-board")
    public ResponseEntity<List<TrackBoardListResponseDTO>> getMusicBoardList() {
//        log.info("spotify auth code: {}", code);
        // service에게 code를 보내면 서비스가 code로 토큰을받고 토큰으로 플레이리스트를 받아서 리액트쪽에주면 끝
        List<TrackBoardListResponseDTO> response = spotifyApiService.getMusicBoardList();
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping("/contents/music-board")
//    public HttpEntity<List<TrackBoardListResponseDTO>> getMusicBoardList(@RequestParam("code") String code) {
//        log.info("Spotify auth code: {}", code);
//        SpotifyApi getSpotifyApi = spotifyApiService.getSpotifyApi(code);
//        List<TrackBoardListResponseDTO> response = spotifyApiService.getMusicBoardList(getSpotifyApi);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @GetMapping("/contents/music-board/{playListId}")
    public HttpEntity<List<MusicListResponseDTO>> getMusicList(@PathVariable long playListId) {
        List<MusicListResponseDTO> response = spotifyApiService.getMusicList(playListId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

>>>>>>> b4b5e409c0b6a1d7557221fdba145a9c21e6da79
}


