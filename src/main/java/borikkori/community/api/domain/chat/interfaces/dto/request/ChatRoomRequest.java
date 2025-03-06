package borikkori.community.api.domain.chat.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;
import borikkori.community.api.common.enums.ChatRoomType;
import borikkori.community.api.common.enums.MbtiType;

import java.util.List;

@Builder
@Getter
public class ChatRoomRequest {

    private ChatRoomType chatRoomType;
    private MbtiType mbtiType;
}
