package borikkori.community.api.adapter.in.web.chat.response;

import borikkori.community.api.common.enums.ChatRoomType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomResponse {

	private Long chatRoomId;
	private String name;
	private ChatRoomType type;
}
