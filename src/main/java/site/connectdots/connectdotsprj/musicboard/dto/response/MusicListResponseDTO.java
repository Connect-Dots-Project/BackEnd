package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;



@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicListResponseDTO {

    private Long musicBoardIdx;

    private String spotifyMusicId;

    private String musicBoardTitle;

    private String musicBoardArtist;

    private String musicBoardTitleImage;

    private String musicBoardPreviewUrl;

    private String musicBoardTrack;
    private String musicBoardTrackImage;

}