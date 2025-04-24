package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;

import borikkori.community.api.common.enums.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLike {
	private final PostLikeId id;
	private final ReactionType reactionType;
	private final LocalDateTime regDate;

}
