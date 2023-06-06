package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotifyPlaylistDTO {
    private String id; // 재생 목록의 고유 식별자
    private String name; // 재생 목록 이름
    private String description; // 재생 목록 설명
    private String href; // 재생 목록의 Spotify API 링크

}


