package site.connectdots.connectdotsprj.musicboard.api;


import net.bytebuddy.matcher.StringMatcher;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;
import java.net.URI;
import java.util.*;


@RestController
@CrossOrigin
public class SpotifyApi {

    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8181/contents/music-board");

    private static final se.michaelthelin.spotify.SpotifyApi spotifyApi = new se.michaelthelin.spotify.SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    //getmapping Postmapping으로 변경

    @GetMapping("/spotify-login")
    public ModelAndView spotifyLogin() {
        String s = "redirect:https://accounts.spotify.com/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;

        return new ModelAndView(s);
    }

    @GetMapping("/contents/music-board")
    public void oauth(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());


        Map<Integer, String> playlistIds = new HashMap<>();
        playlistIds.put(1, "37i9dQZF1DWT9uTRZAYj0c");
        playlistIds.put(2, "37i9dQZEVXbMDoHDwVN2tF");
        playlistIds.put(3 "37i9dQZF1DWWwaxRea1LWS");
        playlistIds.put(4 "5wBovveXvw0woKL72NqA0P");
        playlistIds.put(5, "6OlqhvuJE4cdphMx610BtM");
        playlistIds.put(6, "37i9dQZF1DWTY99d0AYptp");
        playlistIds.put(7, "37i9dQZF1EIUCUQCE4UP0F");
        playlistIds.put(8, "0WOCvglRdeDmMnt8zFV0vc");
        playlistIds.put(9, "37i9dQZF1DXbhErEye1cVu");
        playlistIds.put(10, "37i9dQZF1DXbhErEye1cVu");


        for (Map.Entry<String, String> entry : playlistIds.entrySet()) {
            final Playlist playlist = spotifyApi.getPlaylist(entry.getValue()).build().execute();
            String name = playlist.getName();
            Image[] images = playlist.getImages();
            System.out.println(name);
            for (Image image : images) {
                String url = image.getUrl();
                System.out.println("url = " + url);
            }

            // 해당 플레이리스트의 트랙 정보 가져오기
            Paging<PlaylistTrack> playlistTracksInfo = playlist.getTracks();
            PlaylistTrack[] playlistTracksArray = playlistTracksInfo.getItems();
            for (PlaylistTrack playlistTrack : playlistTracksArray) {
                IPlaylistItem track = playlistTrack.getTrack();
                System.out.println("Track Name: " + track.getName());
            }

        }
    }


    @GetMapping("/contents/music-board/{musicIdx}")
    public void oauth(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());


        Map<Integer, String> playlistIds = new HashMap<>();
        playlistIds.put(1, "37i9dQZF1DWT9uTRZAYj0c");
        playlistIds.put(2, "37i9dQZEVXbMDoHDwVN2tF");
        playlistIds.put(3 "37i9dQZF1DWWwaxRea1LWS");
        playlistIds.put(4 "5wBovveXvw0woKL72NqA0P");
        playlistIds.put(5, "6OlqhvuJE4cdphMx610BtM");
        playlistIds.put(6, "37i9dQZF1DWTY99d0AYptp");
        playlistIds.put(7, "37i9dQZF1EIUCUQCE4UP0F");
        playlistIds.put(8, "0WOCvglRdeDmMnt8zFV0vc");
        playlistIds.put(9, "37i9dQZF1DXbhErEye1cVu");
        playlistIds.put(10, "37i9dQZF1DXbhErEye1cVu");


        for (Map.Entry<String, String> entry : playlistIds.entrySet()) {
            final Playlist playlist = spotifyApi.getPlaylist(entry.getValue()).build().execute();
            String name = playlist.getName();
            System.out.println(name);


            List<String> information = new ArrayList<>();
            String[] split = info.split(",");
            boolean[] visited = new boolean[3];


            for (String s : split) {
                System.out.println(s.trim());
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

            System.out.println(information.get(0).replaceAll("artists=\\[ArtistSimplified\\(name=", "").trim());
            System.out.println(information.get(1).replaceAll("url=", "").trim());
            System.out.println(information.get(2).replaceAll("previewUrl=", "").trim());


        }
    }
}


}






