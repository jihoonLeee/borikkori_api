package wagwagt.community.api.interfaces.controller.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.domain.Comment;
import wagwagt.community.api.entities.domain.CommentLike;
import wagwagt.community.api.entities.domain.CommentLikeId;
import wagwagt.community.api.interfaces.controller.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {


    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }

    public Comment findById(Long id){
        return em.find(Comment.class,id);
    }

    public Optional<List<Comment>> findByPostId(Long postId, int page, int size) {
        List<Comment> comments =  em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId order by c.id desc", Comment.class)
                .setFirstResult((page-1)*size)
                .setMaxResults(size)
                .setParameter("postId", postId)
                .getResultList();
        return comments.isEmpty() ? Optional.empty() : Optional.ofNullable(comments);
    }

    public long findCommentCounts(Long postId){
        return em.createQuery("select count(c) from Comment c WHERE c.post.id = :postId", Long.class)
                .setParameter("postId",postId)
                .getSingleResult();
    }

    public void commentLike(CommentLike commentLike){
        em.persist(commentLike);
    }

//    public boolean likeDupleCheck(CommentLikeId commentLikeId){
//        CommentLike commentLike = em.find(CommentLike.class,commentLikeId);
//        return commentLike == null ? true : false;
//    }
}
