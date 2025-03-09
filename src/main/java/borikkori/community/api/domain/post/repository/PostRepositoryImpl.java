package borikkori.community.api.domain.post.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.PostStatus;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager em;

    public Long save(PostEntity postEntity){
        em.persist(postEntity);
        return postEntity.getId();
    }

    public List<PostEntity> findAll(int page , int size){
        return em.createQuery("select p from Post p where p.postStatus = :postStatus order by p.id desc", PostEntity.class)
                .setParameter("postStatus",PostStatus.PUBLISHED)
                .setFirstResult((page - 1)*size)
                .setMaxResults(size)
                .getResultList();
    }
    public long findPostCounts(){
        return em.createQuery("select count(p) from Post p", Long.class)
                .getSingleResult();
    }

    public PostEntity findById(Long id ){
        return em.find(PostEntity.class,id);
    }

    public void postLike(PostLikeEntity postLikeEntity){
        em.persist(postLikeEntity);
    }

    @Override
    public Optional<PostEntity> findTempByUser(UserEntity user) {
        List<PostEntity> postEntities = em.createQuery("select p from Post p where p.postStatus = :postStatus and p.user = :user order by p.id desc", PostEntity.class)
                .setParameter("postStatus", PostStatus.DRAFT)
                .setParameter("user", user)
                .setMaxResults(1)
                .getResultList();
        return postEntities.stream().findFirst();
    }

    @Override
    public void delete(PostEntity postEntity) {
        if(postEntity != null){
            em.remove(postEntity);
        }
    }

//    public boolean likeDupleCheck(PostLikeId postLikeId){
//        PostLike postLike = em.find(PostLike.class,postLikeId);
//
//        return postLike == null ? true : false;
//    }
}
