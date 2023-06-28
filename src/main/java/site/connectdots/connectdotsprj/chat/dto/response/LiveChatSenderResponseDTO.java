package site.connectdots.connectdotsprj.chat.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class LiveChatSenderResponseDTO {

    private String senderProfile;
    private Boolean isSender;
}
