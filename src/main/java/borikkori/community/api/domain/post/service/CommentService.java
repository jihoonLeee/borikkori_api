package borikkori.community.api.domain.post.service;

import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.user.entity.User;

public interface CommentService {

	Comment createComment(Post post, User user, String contents, Comment parentComment);

	void updateComment(Comment comment, String newContents);

	void processLike(Comment comment);
}
