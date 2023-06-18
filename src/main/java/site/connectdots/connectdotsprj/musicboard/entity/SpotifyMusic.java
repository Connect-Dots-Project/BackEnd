package site.connectdots.connectdotsprj.musicboard.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "musicBoardIdx")
@Entity
@Table(name = "TB_SPOTIFY_MUSIC")
public class SpotifyMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicBoardIdx;

    @Column(nullable = false, length = 50)
    private String spotifyMusicId;

    @Column(nullable = false, length = 1000)
    private String musicBoardTitle;

    @Column(nullable = false, length = 50)
    private String musicBoardArtist;

    @Column(nullable = false, length = 2000)
    private String musicBoardTitleImage;

    @Column(nullable = true, length = 2000)
    private String musicBoardPreviewUrl;
}