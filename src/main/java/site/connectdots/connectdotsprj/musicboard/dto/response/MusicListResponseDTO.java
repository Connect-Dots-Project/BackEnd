package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;
<<<<<<< HEAD
import site.connectdots.connectdotsprj.musicboard.entity.Music;
=======

import javax.persistence.Column;
>>>>>>> b4b5e409c0b6a1d7557221fdba145a9c21e6da79

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicListResponseDTO {

    private Long musicBoardIdx;
<<<<<<< HEAD
    private String musicBoardTrack;
    private String musicBoardTrackImg;
    private String musicBoardTitle;
    private String musicBoardTitleImg;
    private String musicBoardSinger;
    private  Long musicBoardViewCount;

    public MusicListResponseDTO(Music music){
        this.musicBoardIdx = music.getMusicBoardIdx();
        this.musicBoardTrack=music.getMusicBoardTrack();
        this.musicBoardTrackImg=music.getMusicBoardTrackImg();
        this.musicBoardTitle=music.getMusicBoardTitle();
        this.musicBoardTitleImg= music.getMusicBoardTitleImg();
        this.musicBoardSinger=music.getMusicBoardSinger();
        this.musicBoardViewCount=music.getMusicBoardViewCount();
    }
=======

    private String spotifyMusicId;

    private String musicBoardTitle;

    private String musicBoardArtist;

    private String musicBoardTitleImage;

    private String musicBoardPreviewUrl;
>>>>>>> b4b5e409c0b6a1d7557221fdba145a9c21e6da79

    private String musicBoardTrack;
    private String musicBoardTrackImage;

}