package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.Comment;
import wagwagt.community.api.entities.domain.CommentLike;
import wagwagt.community.api.entities.domain.CommentLikeId;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void save(Comment comment);

    Comment findById(Long id);
    Optional<List<Comment>> findByPostId(Long postId, int page, int size) ;

    long findCommentCounts(Long postId);

    void commentLike(CommentLike commentLike);

    boolean likeDupleCheck(CommentLikeId commentLikeId);
}
