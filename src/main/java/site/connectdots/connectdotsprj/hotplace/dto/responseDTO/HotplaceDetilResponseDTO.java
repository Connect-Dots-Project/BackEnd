package site.connectdots.connectdotsprj.hotplace.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceDetilResponseDTO {

    private Location location;
    private String hotplaceImg;
    private String hotplaceContent;
    private Long hotplaceLikeCount;
    private String hotplaceLatitude;
    private String hotplaceLongitude;
    private String hotplaceName;
    private String hotplaceFullAddress;
    private String kakaoLocation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime hotplaceWriteDate;


    public HotplaceDetilResponseDTO(Hotplace hotplace) {
        this.location = hotplace.getLocation();
        this.hotplaceImg = hotplace.getHotplaceImg();
        this.hotplaceContent = hotplace.getHotplaceContent();
        this.hotplaceLikeCount = hotplace.getHotplaceLikeCount();
        this.hotplaceLatitude = hotplace.getHotplaceLatitude();
        this.hotplaceLongitude = hotplace.getHotplaceLongitude();
        this.hotplaceName = hotplace.getHotplaceName();
        this.hotplaceFullAddress = hotplace.getHotplaceFullAddress(); // 응답 시 필요..?
        this.hotplaceWriteDate = hotplace.getHotplaceWriteDate();
    }


}
