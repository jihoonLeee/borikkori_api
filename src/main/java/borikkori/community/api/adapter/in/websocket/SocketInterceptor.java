package borikkori.community.api.adapter.in.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class SocketInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
		WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		// SecurityContext에서 인증 정보 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 인증되지 않은 경우 연결을 차단
		if (authentication == null || !authentication.isAuthenticated()) {
			return false;
		}

		// 인증된 사용자의 정보를 WebSocketSession에 저장할 수 있습니다.
		attributes.put("user", authentication.getPrincipal());
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
		WebSocketHandler wsHandler, Exception ex) {
		// 핸드셰이크 이후 로직 (필요 시 추가)
	}
}
