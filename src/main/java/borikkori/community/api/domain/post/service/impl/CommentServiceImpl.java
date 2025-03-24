package borikkori.community.api.domain.post.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import borikkori.community.api.common.enums.CommentStatus;
import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.service.CommentService;
import borikkori.community.api.domain.user.entity.User;

@Service
public class CommentServiceImpl implements CommentService {
	@Override
	public Comment createComment(Post post, User user, String contents, Comment parentComment) {
		LocalDateTime now = LocalDateTime.now();
		return new Comment(null, post, user, parentComment, contents, CommentStatus.OPEN, 0, now, now);
	}

	@Override
	public void updateComment(Comment comment, String newContents) {
		comment.updateContent(newContents);
	}

	@Override
	public void processLike(Comment comment) {
		comment.addLike();
	}
}
