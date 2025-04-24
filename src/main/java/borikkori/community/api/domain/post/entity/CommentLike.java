package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;

import borikkori.community.api.common.enums.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLike {
	private final CommentLikeId id;
	private final ReactionType reactionType;
	private final LocalDateTime regDate;

}
