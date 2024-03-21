package wagwagt.community.api.infrastructures.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import wagwagt.community.api.infrastructures.handler.WebSocketChatHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketChatHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // setAllowedOrigins(*) 모든 ip에서 접속 가능
        registry.addHandler(webSocketHandler,"/ws/chat/message").setAllowedOrigins("*");
    }
}
