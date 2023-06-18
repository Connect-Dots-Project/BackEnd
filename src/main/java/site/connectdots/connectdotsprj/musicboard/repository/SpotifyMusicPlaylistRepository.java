package site.connectdots.connectdotsprj.musicboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.musicboard.entity.SpotifyMusicPlaylist;

public interface SpotifyMusicPlaylistRepository extends JpaRepository<SpotifyMusicPlaylist, Long> {


}
