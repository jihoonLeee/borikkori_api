package borikkori.community.api.adapter.in.web.chat.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatRoomListResponse {

    private List<ChatRoomResponse> rooms;
}
