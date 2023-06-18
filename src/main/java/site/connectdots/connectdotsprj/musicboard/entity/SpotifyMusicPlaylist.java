package site.connectdots.connectdotsprj.musicboard.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "TB_SPOTIFY_MUSIC_PLAYLIST")
public class SpotifyMusicPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spotify_playlist_id")
    private SpotifyPlaylist spotifyPlaylist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spotify_music_id")
    private SpotifyMusic spotifyMusic;
}