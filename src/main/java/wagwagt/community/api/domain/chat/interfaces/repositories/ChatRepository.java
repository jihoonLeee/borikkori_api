package wagwagt.community.api.domain.chat.interfaces.repositories;

import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;
import wagwagt.community.api.domain.user.entities.User;

import java.util.List;

public interface ChatRepository {

    void save(ChatMessage chatMessage);

    List<ChatMessage> findByRoom(int page , int size, ChatRoom chatRoom);

    List<ChatRoom> findChatRooms(ChatRoomRequest req);
}
