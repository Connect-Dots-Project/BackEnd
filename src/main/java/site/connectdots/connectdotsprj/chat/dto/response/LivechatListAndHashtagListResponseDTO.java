package site.connectdots.connectdotsprj.chat.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivechatListAndHashtagListResponseDTO {

    List<String> hashtagList;

    List<LivechatListResponseDTO> livechatList;
}
