package site.connectdots.connectdotsprj.hotplace.dto.requestDTO;

import lombok.*;
import org.springframework.lang.NonNull;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

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
//    private String hotplaceImg;
//    @NotBlank
    private String hotplaceContent;
    private String hotplaceLatitude;
    private String hotplaceLongitude;
    private String hotplaceName;
    private String hotplaceFullAddress;
    private String kakaoLocation;
//    @NotNull
//    private Long memberIdx;


    public Hotplace toEntity(String uploadFilePath, Member member) {
        return Hotplace.builder()
                .location(this.location)
                .hotplaceImg(uploadFilePath)
                .hotplaceContent(this.hotplaceContent)
                .hotplaceLatitude(this.hotplaceLatitude)
                .hotplaceLongitude(this.hotplaceLongitude)
                .hotplaceName(this.hotplaceName)
                .hotplaceFullAddress(this.hotplaceFullAddress)
                .kakaoLocation(this.kakaoLocation)
                .member(member)
                .build();
    }
}
