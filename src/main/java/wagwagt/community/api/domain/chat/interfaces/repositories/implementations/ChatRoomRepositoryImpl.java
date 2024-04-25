package wagwagt.community.api.domain.chat.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;
import wagwagt.community.api.domain.chat.interfaces.repositories.ChatRoomRepository;
import wagwagt.community.api.domain.user.entities.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final EntityManager em;

    @Override
    public void save(ChatRoom chatRoom) {
        em.persist(chatRoom);
    }

    @Override
    public ChatRoom findById(Long id) {
        return em.find(ChatRoom.class,id);
    }


    /**
     * --TODO : name이 있을때(mbti타입이 있을떄)에만 [2024-04-20 완료]
     * TODO : 채팅방 관리 좀 더 효율적인 방법 생각해서 바꾸기 (ex 권한 과 역할 이용)
     * */
    public List<ChatRoom> findChatRooms(ChatRoomRequest req) {
        StringBuilder queryBuilder = new StringBuilder("select r from ChatRoom r where r.type = :type");

        if (req.getMbtiType() != null) {
            queryBuilder.append(" or r.name = :name");
        }
        queryBuilder.append(" order by r.id desc");

        Query query = em.createQuery(queryBuilder.toString(), ChatRoom.class);
        query.setParameter("type", req.getChatRoomType());

        if (req.getMbtiType() != null) {
            query.setParameter("name", req.getMbtiType().getType());
        }

        return query.getResultList();
    }


}
