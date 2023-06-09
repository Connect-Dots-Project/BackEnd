package site.connectdots.connectdotsprj.musicboard.api;


import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import se.michaelthelin.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import java.io.IOException;
import java.net.URI;
import java.util.*;


@RestController
public class SpotifyApi {

    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8183/spotify-redirect");

    private static final se.michaelthelin.spotify.SpotifyApi spotifyApi = new se.michaelthelin.spotify.SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    @GetMapping("/spotify-redirect")
    public void oauth(@RequestParam String code) throws IOException, ParseException, SpotifyWebApiException {

//        System.out.println("code = [" + code + "]");

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
//        System.out.println("credentials = [" + credentials + "]");

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());

//        System.out.println("Expires in: " + credentials.getExpiresIn());

        final Paging<Category> categoryPaging = spotifyApi.getListOfCategories().build().execute();

        for (Category category : categoryPaging.getItems()) {
//            System.out.println("category = [" + category + "]");

            GetCategorysPlaylistsRequest getCategoryPlaylistsRequest = spotifyApi
                    .getCategorysPlaylists(category.getId())
                    .build();

            Paging<PlaylistSimplified> playlistPaging = getCategoryPlaylistsRequest.execute();
            for (PlaylistSimplified playlist : playlistPaging.getItems()) {
                if (playlist.getImages() != null && playlist.getImages().length > 0) {
                    for (Image image : playlist.getImages()) {
                        String imageUrl = image.getUrl();
                        System.out.println("Image URL: " + imageUrl);
                    }
                }
            }
        }

        final Playlist playlist = spotifyApi.getPlaylist("37i9dQZF1DX7L8tfJz5HGb").build().execute();

        // 플레이리스트 ID 값을 저장할 리스트
        List<String> playlistIds = new ArrayList<>();

        Paging<PlaylistTrack> tracks = playlist.getTracks();
        PlaylistTrack[] playlistTracks = tracks.getItems();

        // 플레이리스트 ID 값을 변수에 저장
        for (PlaylistTrack playlistTrack : playlistTracks) {
            IPlaylistItem track = playlistTrack.getTrack();
            playlistIds.add(track.getId());
        }

        // 랜덤으로 10개 선택
        Random random = new Random();
        List<String> randomPlaylistIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(playlistIds.size());
            String randomPlaylistId = playlistIds.get(randomIndex);
            randomPlaylistIds.add(randomPlaylistId);
        }

        // 선택된 플레이리스트 ID 출력
        for (String playlistId : randomPlaylistIds) {
            System.out.println("Playlist ID: " + playlistId);
        }

//        Paging<PlaylistTrack> tracks = playlist.getTracks();
//        PlaylistTrack[] playlistTracks = tracks.getItems();
        Image[] images = playlist.getImages();


        for (PlaylistTrack playlistTrack : playlistTracks) {
            IPlaylistItem track = playlistTrack.getTrack();
            String name = track.getName();
            System.out.println("name = " + name); //제목

            ExternalUrl externalUrls = track.getExternalUrls();
            Map<String, String> externalUrls1 = externalUrls.getExternalUrls();

            System.out.println("externalUrls1 = " + externalUrls1);

        }

        for (Image image : images) {
            String url = image.getUrl();
            System.out.println("url = " + url);
        }

    }
}
