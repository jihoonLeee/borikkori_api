package borikkori.community.api.config.websocket;

/*@EnableWebSocketSecurity
@Configuration*/
public class WebSocketSecurityConfig {
/*    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        return messages
                .simpTypeMatchers(SimpMessageType.CONNECT).authenticated() // 웹소켓 연결시에만 인증 확인(인증 정보는 WebSecurity(ex. formLogin)를 통해 인증한 정보를 활용)
                .anyMessage().permitAll()
                .build();
    }

    @Bean("csrfChannelInterceptor") // for disable csrf
    public ChannelInterceptor csrfChannelInterceptor() {
        return new ChannelInterceptor() {
        };
    }*/
}
