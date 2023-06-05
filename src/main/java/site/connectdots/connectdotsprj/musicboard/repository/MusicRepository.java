package site.connectdots.connectdotsprj.musicboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.musicboard.entity.Music;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, String> {


    List<Music> findById(Music music);
}
