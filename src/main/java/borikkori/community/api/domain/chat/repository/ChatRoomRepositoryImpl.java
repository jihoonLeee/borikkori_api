package borikkori.community.api.domain.chat.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.chat.entity.ChatRoomEntity;
import borikkori.community.api.adapter.in.web.chat.request.ChatRoomRequest;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final EntityManager em;

    @Override
    public void save(ChatRoomEntity chatRoomEntity) {
        em.persist(chatRoomEntity);
    }

    @Override
    public ChatRoomEntity findById(Long id) {
        return em.find(ChatRoomEntity.class,id);
    }


    /**
     * --TODO : name이 있을때(mbti타입이 있을떄)에만 [2024-04-20 완료]
     * TODO : 채팅방 관리 좀 더 효율적인 방법 생각해서 바꾸기 (ex 권한 과 역할 이용)
     * */
    public List<ChatRoomEntity> findChatRooms(ChatRoomRequest req) {
        StringBuilder queryBuilder = new StringBuilder("select r from ChatRoom r where r.type = :type");

        if (req.getMbtiType() != null) {
            queryBuilder.append(" or r.name = :name");
        }
        queryBuilder.append(" order by r.id desc");

        Query query = em.createQuery(queryBuilder.toString(), ChatRoomEntity.class);
        query.setParameter("type", req.getChatRoomType());

        if (req.getMbtiType() != null) {
            query.setParameter("name", req.getMbtiType().getType());
        }

        return query.getResultList();
    }


}
