package borikkori.community.api.domain.chat.repository;

import borikkori.community.api.adapter.out.persistence.chat.entity.ChatRoomEntity;
import borikkori.community.api.adapter.in.web.chat.request.ChatRoomRequest;

import java.util.List;

public interface ChatRoomRepository {

    void save(ChatRoomEntity chatRoomEntity);

    List<ChatRoomEntity> findChatRooms(ChatRoomRequest req);

    ChatRoomEntity findById(Long id);
}
