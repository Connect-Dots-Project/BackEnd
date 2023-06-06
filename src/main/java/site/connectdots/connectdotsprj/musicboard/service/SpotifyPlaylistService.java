package site.connectdots.connectdotsprj.musicboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.connectdots.connectdotsprj.musicboard.dto.response.SpotifyPlaylistDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SpotifyPlaylistService {

    @Value("${spotify.api.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public SpotifyPlaylistService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SpotifyPlaylistDTO> getPlaylists(String accessToken) {
        String url = baseUrl + "/v1/me/playlists";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyPlaylistDTO[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                SpotifyPlaylistDTO[].class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            SpotifyPlaylistDTO[] playlists = responseEntity.getBody();
            return Arrays.asList(playlists);
        }

        return Collections.emptyList();
    }
}
