package site.connectdots.connectdotsprj.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 웹 소켓에 접근하는 엔드 포인트를 설정해준다.
     * 접근하는 오리진을 설정해준다.
     * JS 라이브러리인 SockJs 를 사용하여 웹 소켓 통신을 지원한다.
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/contents/chat/live") // 웹 소켓에 접속하는 앤드 포인트
                .setAllowedOriginPatterns("*") // 모든 오리진의 접근을 허용
                .withSockJS(); // JS 라이브러리인 SockJs를 사용하여 웹 소켓 통신을 지원
    }

    /**
     * 메시지를 주고 받는 브로커를 활성화 시킨다.
     * Topic : 구독하는 방을 설정해준다.
     * Prefixes : 메시지 송신을 위한 프리픽스
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/queue", "/topic"); // 간단한 브로커 설정 "/queue" 는 단일 수신자 "/topic" 은 다중 수신자
//        registry.enableSimpleBroker("/sub"); // 간단한 브로커 설정 "/queue" 는 단일 수신자 "/topic" 은 다중 수신자
//        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 송신할 때 사용할 프리픽스를 설정한다.
//        registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 메시지를 송신할 때 사용할 프리픽스를 설정한다.

        // 메시지 브로커를 활성화 시키고
        // "/queue" 와 "/topic" 을 주제로 하는 메시지를 지원하며
        // 클라이언트가 메시지를 송신할 때는 "/app" 프리픽스를 사용하여 해당 경로로 라우팅되도록 설정한다.

        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

}
