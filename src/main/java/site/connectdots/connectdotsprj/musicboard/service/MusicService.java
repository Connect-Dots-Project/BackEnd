
package site.connectdots.connectdotsprj.musicboard.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.musicboard.AccessToken;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicDTO;
import site.connectdots.connectdotsprj.musicboard.dto.response.TrackListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.dto.response.LoginSpotifyDTO;
import site.connectdots.connectdotsprj.musicboard.entity.Music;
import site.connectdots.connectdotsprj.musicboard.repository.MusicRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;
    private final AccessToken accessToken;

    public void loginSpotify(LoginSpotifyDTO loginSpotifyDTO) {
        Music music = new Music();
        //여기서부터..
//        music.setClientId(loginSpotifyDTO.);
//        music.setTitle(musicDTO.getTitle());
//        music.setArtist(musicDTO.getArtist());
        // Set other fields as needed

        musicRepository.save(music);
    }

    public TrackListResponseDTO findAll(TrackListResponseDTO musicListResponseDTO) {
        List<Music> musicList = musicRepository.findAll();

        List<TrackListResponseDTO> dto = musicList.stream()
                .map(list -> {
                    return new TrackListResponseDTO(list);
                })
                .collect(toList());


        return musicListResponseDTO;
    }

//    public List<SpotifyPlaylistDTO> getList() {
//
//        return MusicRepository.findAll()
//                .stream()
//                .map(SpotifyPlaylistDTO::new)
//                .collect(toList())
//                ;
//    }




}
