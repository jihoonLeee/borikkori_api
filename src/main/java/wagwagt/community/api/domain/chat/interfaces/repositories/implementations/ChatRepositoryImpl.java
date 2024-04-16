package wagwagt.community.api.domain.chat.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.common.enums.PostStatus;
import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;
import wagwagt.community.api.domain.chat.interfaces.repositories.ChatRepository;
import wagwagt.community.api.domain.post.entities.Post;
import wagwagt.community.api.domain.user.entities.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {

    private final EntityManager em;

    @Override
    public void save(ChatMessage chatMessage) {
        em.persist(chatMessage);
    }

    public List<ChatMessage> findByRoom(int page , int size, ChatRoom chatRoom){
        return em.createQuery("select c from ChatMessage c where c.chatRoom = :chatRoom order by p.id desc",ChatMessage.class)
                .setParameter("chatRoom", chatRoom)
                .setFirstResult((page - 1)*size)
                .setMaxResults(size)
                .getResultList();
    }
    public long findMessageCounts(){
        return em.createQuery("select count(p) from ChatMessage p", Long.class)
                .getSingleResult();
    }



    /**
     * TODO : name이 있을때(mbti타입이 있을떄)에만   MBTI_GROUP , name 둘다 있으면 그거 보여주고 ALL또는 GROUP 보여주기
     * */
    public List<ChatRoom> findChatRooms(ChatRoomRequest req) {
         // 기본 쿼리 문자열
        String queryString = "select r from ChatRoom r where r.type IN :types";

        if (req.getMbtiType() != null ) {
            queryString += " or r.name = :name";
        }
        queryString += " order by r.id desc";

        Query query = em.createQuery(queryString, ChatRoom.class);
        query.setParameter("types", req.getChatRoomTypes());
        if (req.getMbtiType() != null) {
            query.setParameter("name", req.getMbtiType().getType());
        }

        return query.getResultList();
    }

}
