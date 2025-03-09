package borikkori.community.api.adapter.in.web.chat.response;

import lombok.Builder;
import lombok.Getter;
import borikkori.community.api.common.enums.ChatRoomType;

@Builder
@Getter
public class ChatRoomResponse {

    private Long chatRoomId;
    private String name;
    private ChatRoomType type;
}
