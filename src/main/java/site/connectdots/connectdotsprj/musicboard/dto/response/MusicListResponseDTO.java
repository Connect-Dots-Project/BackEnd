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

    private Long music_board_idx;
    private String music_board_title;
    private String music_board_singer;
    private String music_board_lyrics;
    private Genre music_board_genre;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createDate;
    private  Long viewCount;

    public MusicListResponseDTO(Music music){
        this.music_board_idx=music.getMusic_board_idx();
        this.music_board_title=music.getMusic_board_title();
        this.music_board_singer=music.getMusic_board_singer();
        this.music_board_lyrics=music.getMusic_board_lyrics();
        this.music_board_genre=music.getMusic_board_genre();
        this.createDate=music.getCreateDate();
        this.viewCount=music.getViewCount();

    }

}
