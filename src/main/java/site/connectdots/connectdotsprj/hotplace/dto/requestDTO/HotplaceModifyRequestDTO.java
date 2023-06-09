package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceModifyRequestDTO {

    private Long hotplaceIdx;
    private Location hotplaceLocation;
    private String hotplaceImg;
    @NotBlank(message = "수정할 내용을 입력하세요.")
    private String hotplaceContent;
    private String hotplaceLatitude;
    private String hotplaceLongitude;
    private String hotplaceName;
    private String hotplaceFullAddress;


    public void updateHotplace(Hotplace hotplace) {
        hotplace.setLocation(this.hotplaceLocation);
        hotplace.setHotplaceImg(this.hotplaceImg);
        hotplace.setHotplaceContent(this.hotplaceContent);
    }

}
