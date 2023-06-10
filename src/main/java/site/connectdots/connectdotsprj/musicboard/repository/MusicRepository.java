package site.connectdots.connectdotsprj.musicboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.musicboard.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {



}
