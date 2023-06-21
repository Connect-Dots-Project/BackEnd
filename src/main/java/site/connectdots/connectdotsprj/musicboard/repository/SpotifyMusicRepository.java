package site.connectdots.connectdotsprj.musicboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.musicboard.entity.SpotifyMusic;

public interface SpotifyMusicRepository extends JpaRepository<SpotifyMusic, Long> {



}
