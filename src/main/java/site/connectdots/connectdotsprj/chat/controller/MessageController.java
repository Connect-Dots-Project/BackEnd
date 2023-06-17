package site.connectdots.connectdotsprj.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import site.connectdots.connectdotsprj.chat.entity.ChatMessage;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    /**
     * @param message
     * @MessageMapping("/chat/message") : 클라이언트로부터 메시지를 수신하기 위한 메시지 매핑을 처리 해당 경로로 메시지를 전송할 수 있음
     */
    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {

        System.out.println();
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println(message.toString());
        System.out.println(message.getMessage());
        System.out.println(message.getType());
        System.out.println(message.getRoomId());
        System.out.println(message.getSender());
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println();


        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다."); // 메시지 타입이 ENTER 면 입장하였다는 메시지를 뿌려줍니다.
        }
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
        // 수정된 메시지를 해당 방의 주제("/topic/chat/room/{roomId}"로 전송합니다.
        // 구독하고 있는 클라이언트들은 메시지를 수신할 수 있습니다.
    }

}
