package site.connectdots.connectdotsprj.freeboard.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class FreeBoardLikeResultResponseDTO {
    private Long count;
    private String message;
}
