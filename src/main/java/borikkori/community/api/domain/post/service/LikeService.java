package borikkori.community.api.domain.post.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface LikeService {
     <T> boolean isLikeNotExists(Class<T> entityClass, Object  id);
}
