package site.connectdots.connectdotsprj.music.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.music.entity.Music;

public interface MusicRepository extends JpaRepository<Music, String> {

}
