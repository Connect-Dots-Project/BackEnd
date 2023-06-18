package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicListResponseDTO {

    private Long id;

    private String spotifyMusicId;

    private String title;

    private String artist;

    private String image;

    private String previewUrl;
}