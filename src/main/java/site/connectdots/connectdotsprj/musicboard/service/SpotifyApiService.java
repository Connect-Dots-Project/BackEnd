
package site.connectdots.connectdotsprj.musicboard.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import site.connectdots.connectdotsprj.freeboard.dto.response.FreeBoardDetailResponseDTO;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.exception.custom.LikeAndHateException;
import site.connectdots.connectdotsprj.freeboard.exception.custom.NotFoundFreeBoardException;
import site.connectdots.connectdotsprj.musicboard.dto.response.TrackBoardListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.entity.SpotifyMusic;
import site.connectdots.connectdotsprj.musicboard.entity.SpotifyMusicPlaylist;
import site.connectdots.connectdotsprj.musicboard.entity.SpotifyPlaylist;
import site.connectdots.connectdotsprj.musicboard.exception.custom.NotFoundMusicBoardException;
import site.connectdots.connectdotsprj.musicboard.repository.SpotifyMusicPlaylistRepository;
import site.connectdots.connectdotsprj.musicboard.repository.SpotifyMusicRepository;
import site.connectdots.connectdotsprj.musicboard.repository.SpotifyPlaylistRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static site.connectdots.connectdotsprj.musicboard.exception.custom.MusicErrorCode.NOT_FOUND_MUSIC_BOARD;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SpotifyApiService {

    private final SpotifyPlaylistRepository spotifyPlaylistRepository;
    private final SpotifyMusicRepository spotifyMusicRepository;
    private final SpotifyMusicPlaylistRepository spotifyMusicPlaylistRepository;

    private static final String clientId = "e665029ca3b34c27b937c214233fd932";
    private static final String clientSecret = "932abc3385b44159996813d0f82b1284";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8181/contents/music-board");



    public SpotifyApi getSpotifyApi(final String code) {
        try {
            final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRedirectUri(redirectUri)
                    .build();

            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
            final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(credentials.getAccessToken());
            spotifyApi.setRefreshToken(credentials.getRefreshToken());

            return spotifyApi;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String spotifyLogin() {
        return "redirect:https://accounts.spotify.com/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }

//    public List<TrackBoardListResponseDTO> getMusicBoardList(SpotifyApi getSpotifyApi) {
//        List<SpotifyPlaylist> spotifyPlaylists = spotifyPlaylistRepository.findAllBySpotifyApi(getSpotifyApi);
//
//        List<TrackBoardListResponseDTO> musicBoardListResponseDTOList = spotifyPlaylists.stream().map(spotifyPlaylist -> {
//            return TrackBoardListResponseDTO.builder().musicBoardIdx(spotifyPlaylist.getMusicBoardIdx())
//                    .musicBoardPlaylistId(spotifyPlaylist.getMusicBoardPlaylistId())
//                    .musicBoardTrack(spotifyPlaylist.getMusicBoardTrack())
//                    .musicBoardTrackImage(spotifyPlaylist.getMusicBoardTrackImage())
//                    .musicBoardViewCount(spotifyPlaylist.getMusicBoardViewCount())
//                    .build();
//        }).collect(Collectors.toList());
//
//        return musicBoardListResponseDTOList;
//    }
public List<TrackBoardListResponseDTO> getMusicBoardList() {
    try {
        List<SpotifyPlaylist> spotifyPlaylists = spotifyPlaylistRepository.findAll();

        List<TrackBoardListResponseDTO> musicBoardListResponseDTOList = spotifyPlaylists.stream().map(spotifyPlaylist -> {
            return TrackBoardListResponseDTO.builder().musicBoardIdx(spotifyPlaylist.getMusicBoardIdx())
                    .musicBoardPlaylistId(spotifyPlaylist.getMusicBoardPlaylistId())
                    .musicBoardTrack(spotifyPlaylist.getMusicBoardTrack())
                    .musicBoardTrackImage(spotifyPlaylist.getMusicBoardTrackImage())
                    .musicBoardViewCount(spotifyPlaylist.getMusicBoardViewCount())
                    .build();
        }).collect(Collectors.toList());

        return musicBoardListResponseDTOList;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


    public Playlist getPlaylist(final SpotifyApi spotifyApi, final String playListId) {
        try {
            return spotifyApi.getPlaylist(playListId).build().execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Track getTrack(final SpotifyApi spotifyApi, final String trackId) {
        try {
            return spotifyApi.getTrack(trackId).build().execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//                playlist -> id 스포티파이 음악 ID
//                playlist -> tracks -> items -> track -> artiests -> name : 가수명
//                playlist -> tracks -> items -> track -> previewUrl : url
//                playlist -> name 노래명
//                playlist -> tracks -> album -> images -> 0번째배열
    public String updateMusicBoard(final String code) {

        //스포티파티 api 사용을 위한 spotifyApi 객체 생성.
        SpotifyApi spotifyApi = getSpotifyApi(code);


        //플레이리스트(트랙) 조회
        List<SpotifyPlaylist> spotifyPlaylists = spotifyPlaylistRepository.findAll();


        spotifyPlaylists.forEach(spotifyPlaylist -> {
            Playlist playlist = getPlaylist(spotifyApi, spotifyPlaylist.getMusicBoardPlaylistId());

            Arrays.stream(playlist.getTracks().getItems()).forEach(item -> {
                Track track = getTrack(spotifyApi, item.getTrack().getId());

                SpotifyMusic spotifyMusic = SpotifyMusic.builder()
                        .spotifyMusicId(track.getId())
                        .musicBoardArtist((track.getArtists())[0].getName())
                        .musicBoardPreviewUrl(track.getPreviewUrl())
                        .musicBoardTitle(track.getName())
                        .musicBoardTitleImage((track.getAlbum().getImages())[0].getUrl())
                        .build();
                spotifyMusicRepository.save(spotifyMusic);

                SpotifyMusicPlaylist spotifyMusicPlaylist = SpotifyMusicPlaylist.builder()
                        .spotifyPlaylist(spotifyPlaylist)
                        .spotifyMusic(spotifyMusic)
                        .build();
                spotifyMusicPlaylistRepository.save(spotifyMusicPlaylist);
            });
        });

        return spotifyApi.getAccessToken();
    }



    public List<MusicListResponseDTO> getMusicList(final long playListId) {
        SpotifyPlaylist playlist = spotifyPlaylistRepository.findById(playListId).orElseThrow(() -> new NotFoundMusicBoardException());
        updateViewCount(playlist);
        List<MusicListResponseDTO> response = playlist.getSpotifyMusicPlaylists().stream().map(musicPlaylist -> {
            SpotifyMusic music = musicPlaylist.getSpotifyMusic();
            SpotifyPlaylist track = musicPlaylist.getSpotifyPlaylist();

            return MusicListResponseDTO.builder()
                    .musicBoardIdx(music.getMusicBoardIdx())
                    .musicBoardPreviewUrl(music.getMusicBoardPreviewUrl())
                    .spotifyMusicId(music.getSpotifyMusicId())
                    .musicBoardTitleImage(music.getMusicBoardTitleImage())
                    .musicBoardArtist(music.getMusicBoardArtist())
                    .musicBoardTitle(music.getMusicBoardTitle())
                    .musicBoardTrack(track.getMusicBoardTrack())
                    .musicBoardTrackImage(track.getMusicBoardTrackImage())
                    .build();

        }).collect(Collectors.toList());

        return response;
    }

    private void updateViewCount(SpotifyPlaylist spotifyPlaylist) {
        spotifyPlaylist.setMusicBoardViewCount(spotifyPlaylist.getMusicBoardViewCount()+1);
        spotifyPlaylistRepository.save(spotifyPlaylist);
    }
}
