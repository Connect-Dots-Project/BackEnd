
package site.connectdots.connectdotsprj.musicboard.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.dto.response.SpotifyPlaylistDTO;
import site.connectdots.connectdotsprj.musicboard.entity.Music;
import site.connectdots.connectdotsprj.musicboard.repository.MusicRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;


    public MusicListResponseDTO findAll(MusicListResponseDTO musicListResponseDTO) {
        List<Music> musicList = musicRepository.findAll();

        List<MusicListResponseDTO> dto = musicList.stream()
                .map(list -> {
                    return new MusicListResponseDTO(list);
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