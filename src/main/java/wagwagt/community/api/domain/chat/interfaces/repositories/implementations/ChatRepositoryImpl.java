package wagwagt.community.api.domain.chat.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
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
     * --TODO : name이 있을때(mbti타입이 있을떄)에만 [2024-04-20 완료]
     * TODO : 채팅방 관리 좀 더 효율적인 방법 생각해서 바꾸기 (ex 권한 과 역할 이용)
     * */
    public List<ChatRoom> findChatRooms(ChatRoomRequest req) {
        return em.createQuery("select r from ChatRoom r where r.type = :type or r.name = :name order by r.id desc", ChatRoom.class)
                .setParameter("type", req.getChatRoomType())
                .setParameter("name",req.getMbtiType().getType())
                .getResultList();
    }

}
