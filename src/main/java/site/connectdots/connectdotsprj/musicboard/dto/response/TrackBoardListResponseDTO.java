package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackBoardListResponseDTO {

    private Long musicBoardIdx;

    private String musicBoardPlaylistId;

    private String musicBoardTrack;

    private String musicBoardTrackImage;

    private  Long musicBoardViewCount;
}
