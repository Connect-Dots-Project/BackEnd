
package site.connectdots.connectdotsprj.musicboard.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import site.connectdots.connectdotsprj.musicboard.entity.SpotifyMusic;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MusicRepositoryTest {

    @Autowired
    SpotifyMusicRepository musicRepository;



<<<<<<< HEAD
=======
        SpotifyMusic music = musicRepository.findById(music_board_idx).orElse(null);

        if (music != null) {
            String title = music.getMusicBoardTitle();
            System.out.println("Title: " + title);
            assertEquals("title13", title);
        } else {
            System.out.println("Music not found");
        }
    }
>>>>>>> b4b5e409c0b6a1d7557221fdba145a9c21e6da79


}