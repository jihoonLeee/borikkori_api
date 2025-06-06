package borikkori.community.api.application.domain.chat.dto;

import borikkori.community.api.common.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

	private MessageType messageType;
	private Long chatRoomId;
	private String sender;
	private String message;

	public void setSender(String sender) {
		this.sender = sender;
	}
}
