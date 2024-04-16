package wagwagt.community.api.domain.chat.usecases;

import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;
import wagwagt.community.api.domain.chat.interfaces.dto.request.MessageRequest;
import wagwagt.community.api.domain.chat.interfaces.dto.response.ChatRoomListResponse;

public interface ChatUseCase {

    ChatRoomListResponse getChatRoomList(CustomUserDetails customUserDetails);
    void saveMessage(MessageRequest req);
}
