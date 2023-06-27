package site.connectdots.connectdotsprj.chat.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.chat.entity.Livechat;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivechatListResponseDTO {

    private String hashtag;
    private String content;
    private String memberNickname;

    public LivechatListResponseDTO(Livechat livechat) {
        this.hashtag = livechat.getLivechatHashtag();
        this.content = livechat.getLivechatContent();
        this.memberNickname = livechat.getMemberNickname();
    }

}
