package borikkori.community.api.domain.post.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {


    private final EntityManager em;

    public void save(CommentEntity commentEntity){
        em.persist(commentEntity);
    }

    public CommentEntity findById(Long id){
        return em.find(CommentEntity.class,id);
    }

    public Optional<List<CommentEntity>> findByPostId(Long postId, int page, int size) {
        List<CommentEntity> commentEntities =  em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId order by c.id desc", CommentEntity.class)
                .setFirstResult((page-1)*size)
                .setMaxResults(size)
                .setParameter("postId", postId)
                .getResultList();
        return commentEntities.isEmpty() ? Optional.empty() : Optional.ofNullable(commentEntities);
    }

    public long findCommentCounts(Long postId){
        return em.createQuery("select count(c) from Comment c WHERE c.post.id = :postId", Long.class)
                .setParameter("postId",postId)
                .getSingleResult();
    }

    public void commentLike(CommentLikeEntity commentLikeEntity){
        em.persist(commentLikeEntity);
    }

//    public boolean likeDupleCheck(CommentLikeId commentLikeId){
//        CommentLike commentLike = em.find(CommentLike.class,commentLikeId);
//        return commentLike == null ? true : false;
//    }
}
