package site.connectdots.connectdotsprj.musicboard.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.connectdots.connectdotsprj.musicboard.entity.Genre;
import site.connectdots.connectdotsprj.musicboard.entity.Music;

import java.time.LocalDateTime;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicListResponseDTO {

    private Long musicBoardIdx;
    private String musicBoardTitle;
    private String musicBoardSinger;
    private String musicBoardLyrics;
    private Genre musicBoardGenre;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createDate;
    private  Long viewCount;

    public MusicListResponseDTO(Music music){
        this.musicBoardIdx=music.getMusicBoardIdx();
        this.musicBoardTitle=music.getMusicBoardTitle();
        this.musicBoardSinger=music.getMusicBoardSinger();
        this.musicBoardLyrics=music.getMusicBoardLyrics();
        this.musicBoardGenre=music.getMusicBoardGenre();
        this.createDate=music.getCreateDate();
        this.viewCount=music.getViewCount();

    }

}
