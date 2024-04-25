package wagwagt.community.api.domain.chat.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.repositories.ChatMessageRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final EntityManager em;

    @Override
    public void save(ChatMessage chatMessage) {
        em.persist(chatMessage);
    }

    @Override
    public ChatMessage findById(Long id) {
        return em.find(ChatMessage.class,id);
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
}
