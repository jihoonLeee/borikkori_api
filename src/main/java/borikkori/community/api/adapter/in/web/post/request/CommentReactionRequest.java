package borikkori.community.api.adapter.in.web.post.request;

import borikkori.community.api.common.enums.ReactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReactionRequest {
	private Long commentId;
	private ReactionType reactionType;
}
