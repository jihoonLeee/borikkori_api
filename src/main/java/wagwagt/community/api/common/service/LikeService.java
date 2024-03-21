package wagwagt.community.api.common.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final EntityManager em;
    public <T> boolean likeDupleCheck(Class<T> entityClass, Object  id) {
        T entity = em.find(entityClass, id);
        return entity == null;
    }
}
