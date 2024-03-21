package wagwagt.community.api.domain.post.interfaces.repositories;

import wagwagt.community.api.domain.post.entities.Comment;
import wagwagt.community.api.domain.post.entities.CommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentRepository{
    void save(Comment comment);

    Comment findById(Long id);
    Optional<List<Comment>> findByPostId(Long postId, int page, int size) ;

    long findCommentCounts(Long postId);

    void commentLike(CommentLike commentLike);

}
