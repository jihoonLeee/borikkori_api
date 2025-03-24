package borikkori.community.api.domain.post.service;

public interface LikeService {
	<T> boolean isLikeNotExists(Class<T> entityClass, Object id);
}
