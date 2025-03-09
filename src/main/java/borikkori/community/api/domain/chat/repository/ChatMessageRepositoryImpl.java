package borikkori.community.api.domain.chat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.chat.entity.ChatMessageEntity;
import borikkori.community.api.adapter.out.persistence.chat.entity.ChatRoomEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final EntityManager em;

    @Override
    public void save(ChatMessageEntity chatMessageEntity) {
        em.persist(chatMessageEntity);
    }

    @Override
    public ChatMessageEntity findById(Long id) {
        return em.find(ChatMessageEntity.class,id);
    }

    public List<ChatMessageEntity> findByRoom(int page , int size, ChatRoomEntity chatRoomEntity){
        return em.createQuery("select c from ChatMessage c where c.chatRoom = :chatRoom order by p.id desc", ChatMessageEntity.class)
                .setParameter("chatRoom", chatRoomEntity)
                .setFirstResult((page - 1)*size)
                .setMaxResults(size)
                .getResultList();
    }



    public long findMessageCounts(){
        return em.createQuery("select count(p) from ChatMessage p", Long.class)
                .getSingleResult();
    }
}
