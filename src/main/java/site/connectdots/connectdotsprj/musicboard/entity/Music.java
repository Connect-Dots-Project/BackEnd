package site.connectdots.connectdotsprj.musicboard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
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
    private Long musicBoardIdx;

    @Column(nullable = false)
    private String musicBoardTitle;

    @Column(nullable = false)
    private String musicBoardSinger;

    private String musicBoardLyrics;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Genre musicBoardGenre;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    @ColumnDefault("0")
    private  Long viewCount;


}
