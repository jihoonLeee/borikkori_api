package borikkori.community.api.adapter.in.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import borikkori.community.api.application.domain.chat.dto.ChatMessageDto;
import borikkori.community.api.application.domain.chat.usecase.ChatUseCase;
import borikkori.community.api.common.enums.MessageType;
import borikkori.community.api.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * WebSocket Handler 작성
 * 소켓 통신은 서버와 클라이언트가 1:n으로 관계를 맺는다. 따라서 한 서버에 여러 클라이언트 접속 가능
 * 서버에는 여러 클라이언트가 발송한 메세지를 받아 처리해줄 핸들러가 필요
 * TextWebSocketHandler를 상속받아 핸들러 작성
 * 클라이언트로 받은 메세지를 log로 출력하고 클라이언트로 환영 메세지를 보내줌
 * */
@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
	private final ObjectMapper mapper;
	private final ChatUseCase chatUseCase;
	//private final UserMapper userMapper;
	// 현재 연결된 세션들
	private final Set<WebSocketSession> sessions = new HashSet<>();
	// chatRoomId , {session1,session2...}
	private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

	//소켓 연결 확인
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("{} 연결됨", session.getId());
		/**
		 * TODO : 연결 하면 DB에 업데이트 해서 현재 인원수 체크
		 */
		sessions.add(session);
	}

	// 소켓 통신 시 메시지 전송을 다루는 부분
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("payload {}", payload);
		Object auth = session.getAttributes().get("user");
		// payload(전송되는 데이터) -> chatMessageDto

		ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
		if (!"anonymousUser".equals(auth)) {
			CustomUserDetails customUserDetails = (CustomUserDetails)auth;
			// UserEntity user =userMapper.toEntity(customUserDetails.getUser());
			// chatMessageDto.setSender(user.getName());
		} else {
			chatMessageDto.setSender(auth.toString());
		}

		log.info("session {}", chatMessageDto.toString());

		Long chatRoomId = chatMessageDto.getChatRoomId();
		// 메모리상에 채팅방에 대한 세션이 없으면 만들어줌
		if (!chatRoomSessionMap.containsKey(chatRoomId)) {
			chatRoomSessionMap.put(chatRoomId, new HashSet<>());
		}
		Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

		if (chatMessageDto.getMessageType().equals(MessageType.ENTER)) {
			chatRoomSession.add(session);
		}

		//        if(chatRoomSession.size() >= 50){
		//            removeClosedSession(chatRoomSession);
		//        }
		sendMessageToChatRoom(chatMessageDto, chatRoomSession);
	}

	// 소켓 종료 확인
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("{} 연결 끊김", session.getId());
		/**
		 * TODO : 연결 끊기면 DB에서 인원수 감소
		 */
		sessions.remove(session);
	}

	// ====== 채팅 관련 메소드 ======
	private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
		chatRoomSession.removeIf(sess -> !sessions.contains(sess));
	}

	private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
		chatRoomSession.removeIf(session -> !session.isOpen());
		chatRoomSession.forEach(session -> sendMessage(session, chatMessageDto));
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		synchronized (session) {
			try {
				if (session.isOpen()) {
					session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
				}
			} catch (IOException e) {
				log.error("메시지 전송 중 오류 발생", e);
			}
		}
	}
}
