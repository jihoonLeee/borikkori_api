package borikkori.community.api.domain.chat.interfaces.repositories;

import borikkori.community.api.domain.chat.entities.ChatMessage;
import borikkori.community.api.domain.chat.entities.ChatRoom;

import java.util.List;

public interface ChatMessageRepository {

    void save(ChatMessage chatMessage);
    List<ChatMessage> findByRoom(int page , int size, ChatRoom chatRoom);
    ChatMessage findById(Long id);
}
