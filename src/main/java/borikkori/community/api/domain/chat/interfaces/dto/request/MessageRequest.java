package borikkori.community.api.domain.chat.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;
import borikkori.community.api.common.enums.ChatRoomType;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.user.entities.User;

@Getter
@Setter
public class MessageRequest {

    private String message;
    private User sender;

}
