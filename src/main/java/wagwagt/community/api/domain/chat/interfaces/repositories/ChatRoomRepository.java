package wagwagt.community.api.domain.chat.interfaces.repositories;

import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;

import java.util.List;

public interface ChatRoomRepository {

    void save(ChatRoom chatRoom);

    List<ChatRoom> findChatRooms(ChatRoomRequest req);

    ChatRoom findById(Long id);
}
