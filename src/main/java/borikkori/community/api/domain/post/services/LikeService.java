package borikkori.community.api.domain.post.services;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final EntityManager em;
    public <T> boolean likeDupleCheck(Class<T> entityClass, Object  id) {
        T entity = em.find(entityClass, id);
        return entity == null;
    }
}
