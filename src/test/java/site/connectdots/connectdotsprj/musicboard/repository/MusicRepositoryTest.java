
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

//    @Test
//    @DisplayName("bulk insert")
//    void musicList() {
//
////        Music music1 = musicRepository.findById(musicBoardIdx).orElseThrow(
////                () -> new RuntimeException()
////        );
//        for (int i = 0; i < 50; i++) {
//
//            musicRepository.save(
//                    Music.builder()
//                            .musicBoardTitle("Title " + i)
//                            .musicBoardSinger("Singer " + i)
//                            .musicBoardLyrics("Lyrics " + i)
//                            .musicBoardGenre(Genre.values()[i % 7])
//                            //Genre.댄스 + i 로 하고 싶을땐 어떻게 해야할까요?
//                            .build()
//            );
//        }
//    }

    @Test
    @DisplayName("idx14를 조회하면 title13이 나와야한다")
    void musicSearch() {
        long music_board_idx = 14L;

        SpotifyMusic music = musicRepository.findById(music_board_idx).orElse(null);

        if (music != null) {
            String title = music.getMusicBoardTitle();
            System.out.println("Title: " + title);
            assertEquals("title13", title);
        } else {
            System.out.println("Music not found");
        }
    }


}