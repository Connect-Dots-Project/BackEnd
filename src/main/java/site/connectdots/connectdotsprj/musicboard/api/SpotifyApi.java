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
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8190/contents/music-board");

    private static final se.michaelthelin.spotify.SpotifyApi spotifyApi = new se.michaelthelin.spotify.SpotifyApi.Builder()
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
    public void oauth(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());


        Map<String, String> playlistIds = new HashMap<>();
        playlistIds.put("HotHitsKorea50", "37i9dQZF1DWT9uTRZAYj0c");
        playlistIds.put("top50Global", "37i9dQZEVXbMDoHDwVN2tF");
        playlistIds.put("viralHitKorea", "37i9dQZF1DWWwaxRea1LWS");
        playlistIds.put("LeSeraphim", "5wBovveXvw0woKL72NqA0P");
        playlistIds.put("BoBBY", "6OlqhvuJE4cdphMx610BtM");
        playlistIds.put("StrayKids", "37i9dQZF1DWTY99d0AYptp");
        playlistIds.put("KidWine", "37i9dQZF1EIUCUQCE4UP0F");
        playlistIds.put("BTS", "0WOCvglRdeDmMnt8zFV0vc");
        playlistIds.put("Monday", "37i9dQZF1DXbhErEye1cVu");
        playlistIds.put("HappyHits", "37i9dQZF1DXbhErEye1cVu");


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
}
//
//    @GetMapping("/contents/music-board/{playlistName}")
//    public void oauth(@RequestParam String code, @RequestParam String playlistName) throws
//            IOException, ParseException, SpotifyWebApiException {
//
//
//        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
//        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
//
//        spotifyApi.setAccessToken(credentials.getAccessToken());
//        spotifyApi.setRefreshToken(credentials.getRefreshToken());
//
//        Map<String, String> playlistIds = new HashMap<>();
//        playlistIds.put("HotHitsKorea50", "37i9dQZF1DWT9uTRZAYj0c");
//        playlistIds.put("top50Global", "37i9dQZEVXbMDoHDwVN2tF");
//        playlistIds.put("viralHitKorea", "37i9dQZF1DWWwaxRea1LWS");
//        playlistIds.put("LeSeraphim", "5wBovveXvw0woKL72NqA0P");
//        playlistIds.put("BoBBY", "6OlqhvuJE4cdphMx610BtM");
//        playlistIds.put("StrayKids", "37i9dQZF1DWTY99d0AYptp");
//        playlistIds.put("KidWine", "37i9dQZF1EIUCUQCE4UP0F");
//        playlistIds.put("BTS", "0WOCvglRdeDmMnt8zFV0vc");
//        playlistIds.put("Monday", "37i9dQZF1DXbhErEye1cVu");
//        playlistIds.put("HappyHits", "37i9dQZF1DXbhErEye1cVu");
//
//        String playlistId = playlistIds.get(playlistName);
//        if (playlistId != null) {
//            final Playlist playlist = spotifyApi.getPlaylist(playlistId).build().execute();
//            String name = playlist.getName();
//            Image[] images = playlist.getImages();
//            System.out.println(name);
//            for (Image image : images) {
//                String url = image.getUrl();
//                System.out.println("url = " + url);
//            }
//
//            // 해당 플레이리스트의 트랙 정보 가져오기
//            Paging<PlaylistTrack> playlistTracksInfo = playlist.getTracks();
//            PlaylistTrack[] playlistTracksArray = playlistTracksInfo.getItems();
//            for (PlaylistTrack playlistTrack : playlistTracksArray) {
//                IPlaylistItem playlistItem = playlistTrack.getTrack();
//                if (playlistItem instanceof Track) {
//                    Track track = (Track) playlistItem;
//                    System.out.println("Track Name: " + track.getName());
//                    ArtistSimplified[] artists1 = track.getArtists();
//                    System.out.println("artist : "+ artists1 );

                    // 앨범 정보 가져오기
//                    AlbumSimplified album = track.getAlbum();
//                    ArtistSimplified[] artists = track.getArtists();
//                    System.out.println("artitst = " + artists);
//                    String albumName = album.getName();
//                    Image[] albumImages = album.getImages();
//
//                    System.out.println("Album Name: " + albumName);
//                    for (Image image : albumImages) {
//                        String imageUrl = image.getUrl();
//                        System.out.println("Image URL: " + imageUrl);
//                    }

                    // 추가적인 작업 수행...




//        List<String> playlistIds = Arrays.asList("37i9dQZF1DWT9uTRZAYj0c", "37i9dQZEVXbMDoHDwVN2tF", "37i9dQZF1DWWwaxRea1LWS," +
//                                           "5wBovveXvw0woKL72NqA0P", "6OlqhvuJE4cdphMx610BtM", "37i9dQZF1DWTY99d0AYptp", "37i9dQZF1EIUCUQCE4UP0F"
//                                        ,"0WOCvglRdeDmMnt8zFV0vc", "37i9dQZF1DXbhErEye1cVu", "37i9dQZF1DXdPec7aLTmlC"
//        );
//
//        for (String playlistId : playlistIds) {
//            final Playlist playlist = spotifyApi.getPlaylist(playlistId).build().execute();
//            String name = playlist.getName();
//            Image[] images = playlist.getImages();
//            System.out.println(name);
//            for (Image image : images) {
//                String url = image.getUrl();
//                System.out.println("url = " + url);
//            }
//        }









