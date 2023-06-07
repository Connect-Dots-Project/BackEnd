package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.hotplace.entity.HotplaceLocation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @NotNull
//    @Enumerated(EnumType.STRING)
    private HotplaceLocation hotplaceLocation;

    @NotBlank
    private String hotplaceImg;

    @NotBlank
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
