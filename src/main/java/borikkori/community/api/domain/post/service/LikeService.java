package borikkori.community.api.domain.post.service;

public interface LikeService {
	<T> boolean isLikeNotExists(Class<T> entityClass, Object id);

	<T> T findReactionData(Class<T> entityClass, Object id);
}
