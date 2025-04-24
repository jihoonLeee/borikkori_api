package borikkori.community.api.domain.post.service.impl;

import org.springframework.stereotype.Service;

import borikkori.community.api.domain.post.service.LikeService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final EntityManager em;

	public <T> boolean isLikeNotExists(Class<T> entityClass, Object id) {
		return em.find(entityClass, id) == null;
	}

	@Override
	public <T> T findReactionData(Class<T> entityClass, Object id) {
		return em.find(entityClass, id);
	}

}
