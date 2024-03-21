//package wagwagt.community.api.infrastructures.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import wagwagt.community.api.domain.user.entities.enums.Role;
//
//@EnableWebSocketSecurity
//@Configuration
//public class WebSocketSecurityConfig {
//    @Bean
//    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        return messages
//                .simpTypeMatchers(SimpMessageType.CONNECT).authenticated() // 웹소켓 연결시에만 인증 확인(인증 정보는 WebSecurity(ex. formLogin)를 통해 인증한 정보를 활용)
//                .anyMessage().permitAll()
//                .build();
//    }
//
//    @Bean("csrfChannelInterceptor") // for disable csrf
//    public ChannelInterceptor csrfChannelInterceptor() {
//        return new ChannelInterceptor() {
//        };
//    }
//}
