package borikkori.community.api.domain.chat.repository;

import borikkori.community.api.adapter.out.persistence.chat.entity.ChatMessageEntity;
import borikkori.community.api.adapter.out.persistence.chat.entity.ChatRoomEntity;

import java.util.List;

public interface ChatMessageRepository {

    void save(ChatMessageEntity chatMessageEntity);
    List<ChatMessageEntity> findByRoom(int page , int size, ChatRoomEntity chatRoomEntity);
    ChatMessageEntity findById(Long id);
}
