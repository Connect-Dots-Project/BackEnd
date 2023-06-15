//package site.connectdots.connectdotsprj.musicboard.api;
//
//
//import org.apache.hc.core5.http.ParseException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//import se.michaelthelin.spotify.SpotifyHttpManager;
//import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
//import se.michaelthelin.spotify.model_objects.IPlaylistItem;
//import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
//import se.michaelthelin.spotify.model_objects.specification.*;
//import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//
//@RestController
//public class SpotifyApi2 {
//
//    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
//    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
//    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8190/spotify-redirect");
//
//    private static final se.michaelthelin.spotify.SpotifyApi spotifyApi = new se.michaelthelin.spotify.SpotifyApi.Builder()
//            .setClientId(clientId)
//            .setClientSecret(clientSecret)
//            .setRedirectUri(redirectUri)
//            .build();
//
//
//    @GetMapping("/spotify-login")
//    public ModelAndView spotifyLogin() {
//        String s = "redirect:https://accounts.spotify.com/authorize?response_type=code&client_id="+ clientId + "&redirect_uri=" +redirectUri;
//
//        return new ModelAndView(s);
//    }
//
//    @GetMapping("/spotify-redirect")
//    public void oauth(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {
//
////        System.out.println("code = [" + code + "]");
//
//        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
//        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
////        System.out.println("credentials = [" + credentials + "]");
//
//        spotifyApi.setAccessToken(credentials.getAccessToken());
//        spotifyApi.setRefreshToken(credentials.getRefreshToken());
//
////        System.out.println("Expires in: " + credentials.getExpiresIn());
//
//
//        final Playlist playlist = spotifyApi.getPlaylist("37i9dQZEVXbNxXF4SkHj9F").build().execute();
//
//        // 플레이리스트 ID 값을 저장할 리스트
//        List<String> playlistIds = new ArrayList<>();
//
//        Paging<PlaylistTrack> tracks = playlist.getTracks();
//        PlaylistTrack[] playlistTracks = tracks.getItems();
//
//        // 플레이리스트 ID 값을 변수에 저장
//        for (PlaylistTrack playlistTrack : playlistTracks) {
//            IPlaylistItem track = playlistTrack.getTrack();
//            playlistIds.add(track.getId());
//        }
//
//        // 랜덤으로 10개 선택
//        Random random = new Random();
//        List<String> randomPlaylistIds = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            int randomIndex = random.nextInt(playlistIds.size());
//            String randomPlaylistId = playlistIds.get(randomIndex);
//            randomPlaylistIds.add(randomPlaylistId);
//        }
//
//        // 선택된 플레이리스트 ID 및 트랙 정보 출력
//        for (String playlistId : randomPlaylistIds) {
//            Playlist playlistInfo = spotifyApi.getPlaylist(playlistId).build().execute();
//            System.out.println("Playlist ID: " + playlistId);
//            System.out.println("Playlist Name: " + playlistInfo.getName());
//
//            Paging<PlaylistTrack> playlistTracksInfo = playlistInfo.getTracks();
//            PlaylistTrack[] playlistTracksArray = playlistTracksInfo.getItems();
//            for (PlaylistTrack playlistTrack : playlistTracksArray) {
//                IPlaylistItem track = playlistTrack.getTrack();
//                System.out.println("Track Name: " + track.getName());
//                ExternalUrl externalUrls = track.getExternalUrls();
//                Map<String, String> externalUrlsMap = externalUrls.getExternalUrls();
//                System.out.println("External URLs: " + externalUrlsMap);
//            }
//        }
//    }
//}
