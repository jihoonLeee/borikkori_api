package borikkori.community.api.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import borikkori.community.api.adapter.in.websocket.ChatHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;
    private final SocketInterceptor socketInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // setAllowedOrigins(*) 모든 ip에서 접속 가능
        registry.addHandler(chatHandler,"/ws/chat/message/1").addInterceptors(socketInterceptor).setAllowedOrigins("*");
    }
}
