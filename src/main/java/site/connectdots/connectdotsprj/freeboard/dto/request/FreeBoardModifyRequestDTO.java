package site.connectdots.connectdotsprj.freeboard.dto.request;


import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardCategory;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardModifyRequestDTO {
    private Long freeBoardIdx;
    private String freeBoardImg;
    private String freeBoardTitle;
    private String freeBoardContent;
    private String freeBoardLocation;
    private FreeBoardCategory freeBoardCategory;

    private Long memberIdx;

}
