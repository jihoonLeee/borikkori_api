package wagwagt.community.api.domain.chat.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.common.enums.ChatRoomType;

@Builder
@Getter
public class ChatRoomResponse {

    private String name;
    private ChatRoomType type;
}
