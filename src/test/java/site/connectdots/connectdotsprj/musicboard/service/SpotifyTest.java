package site.connectdots.connectdotsprj.musicboard.service;

import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Category;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class SpotifyTest {

    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8181/contents/music-board");

        private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
        private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();

        @Test
        public void authorizationCodeUri_Sync() {
            final URI uri = authorizationCodeUriRequest.execute();

            System.out.println("URI: " + uri.toString());
        }

        @Test
        public void getAuthenticationInfo() throws Exception {
            String code = "AQDmk6Z4wKQbm8T-TeP3PCMRuKXr2pBCfvguQEiMIdI1S-3RF7yBKBdZCDsYI-kTe3Heqxx_YA7cOU7SQ7G_7I99VJrQx69FghXmG-sLhXYw7t6iZxZb5E9Wv6aIDgVarc_uYEls81sEIvnoMwjR4jpeZYYu9FyEdqp1bUyPuDTVBqGZ-5hiQ97r";

            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
            final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
            System.out.println("credentials = [" + credentials + "]");

            spotifyApi.setAccessToken(credentials.getAccessToken());
            spotifyApi.setRefreshToken(credentials.getRefreshToken());

            System.out.println("Expires in: " + credentials.getExpiresIn());

            final Paging<Category> categoryPaging = spotifyApi.getListOfCategories().build().execute();
            for (Category category : categoryPaging.getItems()) {
                System.out.println("category = [" + category + "]");
            }

        }

        @Test
        public void test1() throws Exception {

            String accessToken = "BQAZR4umUUj_dtuR5RMObqvUGAZlVzNqiOye42ZU_nGXmG1yYjclnvYoa0Dv3UWDx_N4PR1e6RKmObciyEmTprrJd8cpoBc_tGYdduaumOtyLnAwwnCZ532YZukwqaq_Dywk98oqdjnoUIfCo5Z4dxBh3O0RBsmY8EYy9nPFHnfXSCo19cbiTtodYmDbmDMJfNVDCQ";
            spotifyApi.setAccessToken(accessToken);

            final Playlist playlist = spotifyApi.getPlaylist("37i9dQZF1DX7L8tfJz5HGb").build().execute();
            System.out.println(playlist);
        }

    }


