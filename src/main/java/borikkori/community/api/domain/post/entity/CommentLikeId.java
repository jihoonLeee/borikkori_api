package borikkori.community.api.domain.post.entity;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeId {
	private final Long commentId;
	private final Long userId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CommentLikeId))
			return false;
		CommentLikeId that = (CommentLikeId)o;
		return Objects.equals(commentId, that.commentId) &&
			Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId, userId);
	}
}
