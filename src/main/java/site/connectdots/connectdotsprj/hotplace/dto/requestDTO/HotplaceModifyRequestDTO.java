package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceModifyRequestDTO {

    private Long hotplaceIdx;
    private Location location;
    private String hotplaceImg;
//    @NotBlank(message = "수정할 내용을 입력하세요.")
    private String hotplaceContent;
    private String hotplaceLatitude;
    private String hotplaceLongitude;
    private String hotplaceName;
    private String hotplaceFullAddress;
    private String kakaoLocation;


    public void updateHotplace(Hotplace hotplace, String uploadFilePath){
        hotplace.setHotplaceImg(uploadFilePath);
        hotplace.setHotplaceContent(this.hotplaceContent);
        hotplace.setHotplaceLatitude(this.hotplaceLatitude);
        hotplace.setHotplaceLongitude(this.hotplaceLongitude);
        hotplace.setHotplaceName(this.hotplaceName);
        hotplace.setHotplaceFullAddress(this.hotplaceFullAddress);
        hotplace.setKakaoLocation(this.kakaoLocation);
    }

}
