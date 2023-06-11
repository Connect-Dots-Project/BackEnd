//package site.connectdots.connectdotsprj.musicboard;
//
//import com.wrapper.spotify.SpotifyApi;
//import com.wrapper.spotify.exceptions.SpotifyWebApiException;
//import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
//import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
//
//import java.io.IOException;
//
//public class accessToken {
//    private static final String CLIENT_ID = "${35101b32b4ef490db10291d6d65c71b0}";
//    private static final String CLIENT_SECRET = "${5d55392d067042beba30b13a55e07286}";
//    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();
//
//    public static String accessToken() {
//        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
//        try {
//            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
//            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
//            return sptifyApi.getAccessToken();
//        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
//            System.out.println("Error: " + e.getMessage());
//            return "error";
//        }
//    }
//
//
//
//}
