package site.connectdots.connectdotsprj.chat.dto.request;

import lombok.*;
import site.connectdots.connectdotsprj.chat.dto.response.LivechatCreateResponseDTO;
import site.connectdots.connectdotsprj.chat.entity.Livechat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class LivechatCreateRequestDTO {
    private String content;
    private String hashTag;

    public Livechat toEntity() {
        return Livechat.builder()
                .livechatContent(this.content)
                .livechatHashtag(this.hashTag)
                .build();
    }
}
