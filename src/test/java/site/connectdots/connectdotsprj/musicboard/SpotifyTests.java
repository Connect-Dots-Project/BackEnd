package site.connectdots.connectdotsprj.musicboard;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class SpotifyTests {

    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8190/spotify-redirect");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();

    @Test
    @DisplayName("로그인 후 uri에 보여지는 코드가 나의 권한 코드여야한다.")
    public void authorizationCodeUri() {
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());
    }

    @Test
    @DisplayName("권한코드를 가지고 accessToken 받아서 설정하고 카테고리별로 조회하면 자료가 나와야한다")
    void getAuthenticationInfo() throws Exception {
        String code = "AQDY6EOKW_xKQ5dSwq1i9gA98Fl7yAY1NbfYIMwUcVULkVvnvLOrnzv-mv7A1V8VO5TUR1kYBP0UWbad2KzRl1xndqt_DgavBfm6VGRR_9JKe5k9BAbzEtfWJjZSnitb9IHoXHI-VyvXjrlZCMYh0ZgEq11sno87WtOsXFcaME5xo7xHAsmxO4CV";
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
//        System.out.println("credentials = [" + credentials + "]");

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());

//        System.out.println("Expires in: " + credentials.getExpiresIn());

        final Paging<Category> categoryPaging = spotifyApi.getListOfCategories().build().execute();

        for (Category category : categoryPaging.getItems()) {
            System.out.println("category = [" + category + "]");
        }

    }

    @Test
    @DisplayName("토큰을 가지고 스포티파이의 플레이리스트 id값을 가져와 부르면 노래리스트가 나와야한다")
    void getPlayList() throws Exception {

        String code = "AQDY6EOKW_xKQ5dSwq1i9gA98Fl7yAY1NbfYIMwUcVULkVvnvLOrnzv-mv7A1V8VO5TUR1kYBP0UWbad2KzRl1xndqt_DgavBfm6VGRR_9JKe5k9BAbzEtfWJjZSnitb9IHoXHI-VyvXjrlZCMYh0ZgEq11sno87WtOsXFcaME5xo7xHAsmxO4CV";
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
//        System.out.println("credentials = [" + credentials + "]");

        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());

//        String accessToken = "AQDY6EOKW_xKQ5dSwq1i9gA98Fl7yAY1NbfYIMwUcVULkVvnvLOrnzv-mv7A1V8VO5TUR1kYBP0UWbad2KzRl1xndqt_DgavBfm6VGRR_9JKe5k9BAbzEtfWJjZSnitb9IHoXHI-VyvXjrlZCMYh0ZgEq11sno87WtOsXFcaME5xo7xHAsmxO4CV";
        //spotifyApi.setAccessToken(accessToken);


        final Playlist playlist = spotifyApi.getPlaylist("37i9dQZF1DX5gQonLbZD9s").build().execute();
        System.out.println(playlist);
    }

    @Test
//    @DisplayName("해당 플레이 리스트에서 가수, 노래제목, 앨범url을 가져와야한다")
    @DisplayName("테스트 실행 시 플레이리스트 안에 지민의 노래정보가 조회되어야한다")
    void getCustomList(Model model) throws Exception {

        String accessToken = "AQDY6EOKW_xKQ5dSwq1i9gA98Fl7yAY1NbfYIMwUcVULkVvnvLOrnzv-mv7A1V8VO5TUR1kYBP0UWbad2KzRl1xndqt_DgavBfm6VGRR_9JKe5k9BAbzEtfWJjZSnitb9IHoXHI-VyvXjrlZCMYh0ZgEq11sno87WtOsXFcaME5xo7xHAsmxO4CV";
        spotifyApi.setAccessToken(accessToken);

        final Playlist playlist = spotifyApi.getPlaylist("37i9dQZF1DX5gQonLbZD9s").build().execute();
        //지민
        final Artist artist = spotifyApi.getArtist("2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6").build().execute();
        System.out.println(playlist);
        System.out.println(artist);



        }
    }
