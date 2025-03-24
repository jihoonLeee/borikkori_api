package borikkori.community.api.adapter.in.web.chat.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponse {

	private List<ChatRoomResponse> rooms;
}
