package borikkori.community.api.domain.chat.interfaces.repositories;

import borikkori.community.api.domain.chat.entities.ChatMessage;
import borikkori.community.api.domain.chat.entities.ChatRoom;
import borikkori.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;

import java.util.List;

public interface ChatRoomRepository {

    void save(ChatRoom chatRoom);

    List<ChatRoom> findChatRooms(ChatRoomRequest req);

    ChatRoom findById(Long id);
}
