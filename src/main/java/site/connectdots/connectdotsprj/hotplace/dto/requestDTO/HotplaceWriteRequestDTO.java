package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceWriteRequestDTO {

//    @NotNull
//    @Enumerated(EnumType.STRING)
    private Location location;
//    @NotBlank
    private String hotplaceImg;
//    @NotBlank
    private String hotplaceContent;
    private String hotplaceLatitude;
    private String hotplaceLongitude;
    private String hotplaceName;
    private String hotplaceFullAddress;

//    private Long memberIdx;


    public Hotplace toEntity() {
        return Hotplace.builder()
                .location(this.location)
                .hotplaceImg(this.hotplaceImg)
                .hotplaceContent(this.hotplaceContent)
                .hotplaceLatitude(this.hotplaceLatitude)
                .hotplaceLongitude(this.hotplaceLongitude)
                .hotplaceName(this.hotplaceName)
                .hotplaceFullAddress(this.hotplaceFullAddress)
                .build();
    }
}
