package borikkori.community.api.adapter.in.web.post.response;

import java.time.LocalDateTime;
import java.util.List;

import borikkori.community.api.common.enums.CommentStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

	private Long commentId;
	private Long parentCommentId;
	private String name;
	private String contents;
	private int likeCnt;
	private CommentStatus status;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	private List<CommentResponse> children;
}
