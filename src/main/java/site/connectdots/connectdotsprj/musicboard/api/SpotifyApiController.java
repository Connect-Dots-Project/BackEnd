package site.connectdots.connectdotsprj.musicboard.api;


import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import site.connectdots.connectdotsprj.musicboard.AccessToken;
import site.connectdots.connectdotsprj.musicboard.entity.Music;
import site.connectdots.connectdotsprj.musicboard.repository.MusicRepository;
import se.michaelthelin.spotify.SpotifyApi;

import java.io.IOException;
import java.util.*;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SpotifyApiController {

    private final MusicRepository musicRepository;
    private final AccessToken accessToken;


    @PostMapping("/spotify-login")
    public ModelAndView spotifyLogin(@RequestBody String authorizationCode) {

        SpotifyApi spotifyApi = AccessToken.getSpotifyApi();

        String s = "redirect:https://accounts.spotify.com/authorize?response_type=code&client_id=" + spotifyApi.getClientId() + "&redirect_uri=" + spotifyApi.getRedirectURI();

        return new ModelAndView(s);
    }

    @GetMapping("/contents/music-board")
    public void TrackList(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {
        SpotifyApi spotifyApi = AccessToken.getSpotifyApi();


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
        playlistIds.put(8L, "0WOCvglRdeDmMnt8zFV0vc");
        playlistIds.put(9L, "37i9dQZF1DXbhErEye1cVu");
        playlistIds.put(10L, "37i9dQZF1DXbhErEye1cVu");

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
        SpotifyApi spotifyApi = AccessToken.getSpotifyApi();

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
}


