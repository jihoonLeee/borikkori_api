package borikkori.community.api.domain.post.entity;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeId {
	private final Long postId;
	private final Long userId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PostLikeId))
			return false;
		PostLikeId that = (PostLikeId)o;
		return Objects.equals(postId, that.postId) &&
			Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, userId);
	}
}
