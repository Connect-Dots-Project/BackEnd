package site.connectdots.connectdotsprj.freeboard.dto.request;


import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardCategory;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardWriteRequestDTO {
    private String freeBoardTitle;
    private String freeBoardContent;
    private String freeBoardLocation;
    private FreeBoardCategory freeBoardCategory;
}
