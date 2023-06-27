package site.connectdots.connectdotsprj.musicboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.michaelthelin.spotify.SpotifyApi;
import site.connectdots.connectdotsprj.musicboard.entity.SpotifyPlaylist;

import java.util.List;

public interface SpotifyPlaylistRepository extends JpaRepository<SpotifyPlaylist, Long> {





}
