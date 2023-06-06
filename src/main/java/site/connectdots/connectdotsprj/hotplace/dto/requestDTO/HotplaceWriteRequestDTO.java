package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.entity.HotplaceLocation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceWriteRequestDTO {

    private HotplaceLocation hotplaceLocation;
    private String hotplaceImg;
    private String hotplaceContent;
    private Long memberIdx;
    //위도경도


    public Hotplace toEntity() {
        return Hotplace.builder()
                .hotplaceLocation(this.hotplaceLocation)
                .hotplaceImg(this.hotplaceImg)
                .hotplaceContent(this.hotplaceContent)
                .build();
    }
}
