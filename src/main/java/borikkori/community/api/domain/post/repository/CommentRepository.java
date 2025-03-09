package borikkori.community.api.domain.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository{
    void save(CommentEntity commentEntity);

    CommentEntity findById(Long id);
    Optional<List<CommentEntity>> findByPostId(Long postId, int page, int size) ;

    long findCommentCounts(Long postId);

    void commentLike(CommentLikeEntity commentLikeEntity);

}
