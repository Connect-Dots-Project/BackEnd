
package site.connectdots.connectdotsprj.musicboard.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.musicboard.repository.MusicRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;




}
