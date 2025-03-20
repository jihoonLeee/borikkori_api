package borikkori.community.api.domain.post.service.impl;

import borikkori.community.api.domain.post.service.LikeService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final EntityManager em;

    public <T> boolean isLikeNotExists(Class<T> entityClass, Object  id) {
        T entity = em.find(entityClass, id);
        return entity == null;
    }
}
