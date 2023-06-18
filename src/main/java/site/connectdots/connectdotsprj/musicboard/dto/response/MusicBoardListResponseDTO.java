package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicBoardListResponseDTO {

    private Long id;

    private String spotifyPlaylistId;

    private String title;

    private String image;

    private  Long musicBoardViewCount;
}
