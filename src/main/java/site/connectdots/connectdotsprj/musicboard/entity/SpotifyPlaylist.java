package site.connectdots.connectdotsprj.musicboard.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "TB_SPOTIFY_PLAYLIST")
public class SpotifyPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicBoardIdx;

    //api 명시되어있는 playlist key값
    @Column(nullable = false, length = 50)
    private String musicBoardPlaylistId;

    @Column(nullable = false, length = 50)
    private String musicBoardTrack;

    @Column(nullable = false, length = 2000)
    private String musicBoardTrackImage;

    @Builder.Default
    private Long musicBoardViewCount = 0L;

    @OneToMany(mappedBy = "spotifyPlaylist")
    private List<SpotifyMusicPlaylist> spotifyMusicPlaylists;
}