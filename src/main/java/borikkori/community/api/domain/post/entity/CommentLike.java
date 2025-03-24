package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLike {
	private final CommentLikeId id;
	private final LocalDateTime regDate;

}
