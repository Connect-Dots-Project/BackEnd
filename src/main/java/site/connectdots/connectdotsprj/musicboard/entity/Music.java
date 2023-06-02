package site.connectdots.connectdotsprj.musicboard.entity;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.DefaultMethod;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder @NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "TB_MUSICBOARD")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long music_board_idx;

    @Column(nullable = false)
    private String music_board_title;

    @Column(nullable = false)
    private String music_board_singer;

    private String music_board_lyrics;


    @Column(nullable = false)
    private Genre music_board_genre;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @ColumnDefault("0")
    private  Long viewCount;


}
