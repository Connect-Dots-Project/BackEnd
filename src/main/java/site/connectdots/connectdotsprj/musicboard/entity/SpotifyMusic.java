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
@Table(name = "TB_SPOTIFY_MUSIC")
public class SpotifyMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String spotifyMusicId;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 50)
    private String artist;

    @Column(nullable = false, length = 2000)
    private String image;

    @Column(nullable = true, length = 2000)
    private String previewUrl;
}