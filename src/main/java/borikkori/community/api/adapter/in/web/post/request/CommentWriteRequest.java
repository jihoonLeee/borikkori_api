package borikkori.community.api.adapter.in.web.post.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWriteRequest {
	private Long postId;
	private Long parentCommentId;
	private String contents;
}
