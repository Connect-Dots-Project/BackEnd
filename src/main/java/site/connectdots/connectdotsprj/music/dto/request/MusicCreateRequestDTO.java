package site.connectdots.connectdotsprj.music.dto.request;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicCreateRequestDTO {


    private String userNum;

    @NotNull
    @Size(min = 2, max = 30)
    private String title;

    @NotNull
    private String songTitle;


}
