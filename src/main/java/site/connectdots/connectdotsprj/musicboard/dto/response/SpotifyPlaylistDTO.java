package site.connectdots.connectdotsprj.musicboard.dto.response;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotifyPlaylistDTO {
        private String artist;
        private String trackName;
        private String albumUrl;
        private String PlaylistCoverImageUrl;

        // 생성자, getter 및 setter 생략

        @Override
        public String toString() {
            return "PlaylistItem{" +
                    "artist='" + artist + '\'' +
                    ", trackName='" + trackName + '\'' +
                    ", albumUrl='" + albumUrl + '\'' +
                    ", PlaylistCoverImageUrl='" + PlaylistCoverImageUrl + '\'' +
                    '}';
        }

}




