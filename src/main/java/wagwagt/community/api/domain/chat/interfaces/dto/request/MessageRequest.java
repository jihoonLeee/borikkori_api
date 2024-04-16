package wagwagt.community.api.domain.chat.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;
import wagwagt.community.api.common.enums.ChatRoomType;
import wagwagt.community.api.common.enums.MbtiType;

@Getter
@Setter
public class MessageRequest {

    private String message;
    private String email;
    private MbtiType mbtiType;

}
