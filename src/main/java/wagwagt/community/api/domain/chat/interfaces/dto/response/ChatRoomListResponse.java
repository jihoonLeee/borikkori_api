package wagwagt.community.api.domain.chat.interfaces.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatRoomListResponse {

    private List<ChatRoomResponse> rooms;
}
