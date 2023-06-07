package site.connectdots.connectdotsprj.hotplace.dto.responseDTO;

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
public class HotplaceResponseDTO {

    private HotplaceLocation hotplaceLocation;
    private String hotplaceImg;
    private String hotplaceContent;
    private Long hotplaceLikeCount;
    private String memberNickname;
    // 위도경도-지도

    public HotplaceResponseDTO(Hotplace hotplace) {
        this.hotplaceLocation = hotplace.getHotplaceLocation();
        this.hotplaceImg = hotplace.getHotplaceImg();
        this.hotplaceContent = hotplace.getHotplaceContent();
        this.hotplaceLikeCount = hotplace.getHotplaceLikeCount();
        this.memberNickname = hotplace.getMember().getMemberNickname();
        // 이거맞낭..?
    }


}
