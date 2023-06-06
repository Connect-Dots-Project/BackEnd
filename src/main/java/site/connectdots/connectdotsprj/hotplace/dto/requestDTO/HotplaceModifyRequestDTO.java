package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.entity.HotplaceLocation;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceModifyRequestDTO {

    private HotplaceLocation hotplaceLocation;
    private String hotplaceImg;
    @NotBlank(message = "수정할 내용을 입력하세요.")
    private String hotplaceContent;
    //위도경도


    public void updateHotplace(Hotplace hotplace) {
        hotplace.setHotplaceLocation(this.hotplaceLocation);
        hotplace.setHotplaceImg(this.hotplaceImg);
        hotplace.setHotplaceContent(this.hotplaceContent);
    }

}
