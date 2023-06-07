package site.connectdots.connectdotsprj.hotplace.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.entity.HotplaceLocation;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceDetilResponseDTO {

    private HotplaceLocation hotplaceLocation;
    private String hotplaceImg;
    private String hotplaceContent;
    private Long hotplaceLikeCount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime hotplaceWriteDate;
    // 위도경도-지도

    public HotplaceDetilResponseDTO(Hotplace hotplace) {
        this.hotplaceLocation = hotplace.getHotplaceLocation();
        this.hotplaceImg = hotplace.getHotplaceImg();
        this.hotplaceContent = hotplace.getHotplaceContent();
        this.hotplaceLikeCount = hotplace.getHotplaceLikeCount();
        this.hotplaceWriteDate = hotplace.getHotplaceWriteDate();
    }


}
