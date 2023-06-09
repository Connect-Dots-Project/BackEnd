package site.connectdots.connectdotsprj.hotplace.dto.responseDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class HotplaceListResponseDTO {

    private List<HotplaceDetilResponseDTO> hotplaceList;
}
