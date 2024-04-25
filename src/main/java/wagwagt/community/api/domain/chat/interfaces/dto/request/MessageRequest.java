package wagwagt.community.api.domain.chat.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;
import wagwagt.community.api.common.enums.ChatRoomType;
import wagwagt.community.api.common.enums.MbtiType;
import wagwagt.community.api.domain.user.entities.User;

@Getter
@Setter
public class MessageRequest {

    private String message;
    private User sender;

}
