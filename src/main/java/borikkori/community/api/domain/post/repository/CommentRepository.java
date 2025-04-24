package borikkori.community.api.domain.post.repository;

import org.springframework.data.domain.Page;

import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.CommentLike;

public interface CommentRepository {
	Long saveComment(Comment comment);

	Comment findCommentById(Long id);

	Page<Comment> findCommentList(Long postId, int page, int size);

	Long countByPostId(Long postId);

	void saveCommentLike(CommentLike commentLike);

}
