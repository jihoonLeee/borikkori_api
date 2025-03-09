package borikkori.community.api.adapter.in.web.chat.request;

import lombok.Builder;
import lombok.Getter;
import borikkori.community.api.common.enums.ChatRoomType;
import borikkori.community.api.common.enums.MbtiType;

@Builder
@Getter
public class ChatRoomRequest {

    private ChatRoomType chatRoomType;
    private MbtiType mbtiType;
}
