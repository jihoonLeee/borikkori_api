package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLike {
	private final PostLikeId id;
	private final LocalDateTime regDate;

}
