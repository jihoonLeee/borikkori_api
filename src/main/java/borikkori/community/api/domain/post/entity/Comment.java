package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;

import borikkori.community.api.common.enums.CommentStatus;
import borikkori.community.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment {
	private Long id;
	private Post post;
	private User user;
	private Comment parentComment;  // 상위 댓글 (없을 수도 있음)
	private String contents;
	private CommentStatus commentStatus;
	private int likeCount;
	private LocalDateTime regDate;
	private LocalDateTime updDate;

	public void updateContent(String contents) {
		if (contents == null || contents.isEmpty()) {
			throw new IllegalArgumentException("Contents must not be empty");
		}
		this.contents = contents;
		updateModificationDate();
	}

	public void addLike() {
		this.likeCount++;
		updateModificationDate();
	}

	private void updateModificationDate() {
		this.updDate = LocalDateTime.now();
	}
}
