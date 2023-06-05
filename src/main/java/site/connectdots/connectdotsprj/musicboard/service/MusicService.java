
package site.connectdots.connectdotsprj.musicboard.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.musicboard.dto.response.MusicListResponseDTO;
import site.connectdots.connectdotsprj.musicboard.entity.Music;
import site.connectdots.connectdotsprj.musicboard.repository.MusicRepository;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());


        return musicListResponseDTO;
    }


}
