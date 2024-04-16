package wagwagt.community.api.domain.chat.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.common.enums.ChatRoomType;
import wagwagt.community.api.common.enums.MbtiType;

import java.util.List;

@Builder
@Getter
public class ChatRoomRequest {

    private List<ChatRoomType> chatRoomTypes;
    private MbtiType mbtiType;
}
