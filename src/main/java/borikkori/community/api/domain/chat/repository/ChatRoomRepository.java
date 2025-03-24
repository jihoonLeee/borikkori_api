package borikkori.community.api.domain.chat.repository;

import java.util.List;

import borikkori.community.api.adapter.in.web.chat.request.ChatRoomRequest;
import borikkori.community.api.adapter.out.persistence.chat.entity.ChatRoomEntity;

public interface ChatRoomRepository {

	void save(ChatRoomEntity chatRoomEntity);

	List<ChatRoomEntity> findChatRooms(ChatRoomRequest req);

	ChatRoomEntity findById(Long id);
}
