package site.connectdots.connectdotsprj.hotplace.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceDetailResponseDTO {

    private Long hotplaceIdx;
    private Location location;
    private String hotplaceImg;
    private String hotplaceContent;
    private String hotplaceLatitude;
    private String hotplaceLongitude;
    private String hotplaceName;
    private String hotplaceFullAddress;
    private String kakaoLocation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime hotplaceWriteDate;


    public HotplaceDetailResponseDTO(Hotplace hotplace) {
        this.hotplaceIdx = hotplace.getHotplaceIdx();
        this.location = hotplace.getLocation();
        this.hotplaceImg = hotplace.getHotplaceImg();
        this.hotplaceContent = hotplace.getHotplaceContent();
        this.hotplaceLatitude = hotplace.getHotplaceLatitude();
        this.hotplaceLongitude = hotplace.getHotplaceLongitude();
        this.hotplaceName = hotplace.getHotplaceName();
        this.hotplaceFullAddress = hotplace.getHotplaceFullAddress();
        this.kakaoLocation = hotplace.getKakaoLocation();
        this.hotplaceWriteDate = hotplace.getHotplaceWriteDate();
    }


}
