package borikkori.community.api.domain.post.repository;

import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.CommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentRepository{
    void saveComment(Comment comment);

    Comment findCommentById(Long id);

    Optional<List<Comment>> findCommentsByPostId(Long postId, int page, int size);

    Long countByPostId(Long postId);

    void saveCommentLike(CommentLike commentLike);

}
