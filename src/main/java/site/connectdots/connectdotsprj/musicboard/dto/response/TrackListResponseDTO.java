package site.connectdots.connectdotsprj.musicboard.dto.response;


import lombok.*;
import site.connectdots.connectdotsprj.musicboard.entity.Music;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackListResponseDTO {

    private Long musicBoardIdx;
    private String musicBoardTrack;
    private String musicBoardTrackImg;
    private  Long musicBoardViewCount;

    public TrackListResponseDTO(Music music){
        this.musicBoardIdx=music.getMusicBoardIdx();
        this.musicBoardTrack=music.getMusicBoardTrack();
        this.musicBoardTrackImg=music.getMusicBoardTrackImg();
        this.musicBoardViewCount=music.getMusicBoardViewCount();

    }

}
