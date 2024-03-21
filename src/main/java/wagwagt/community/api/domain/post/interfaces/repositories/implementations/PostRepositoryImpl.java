package wagwagt.community.api.domain.post.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.domain.post.entities.Post;
import wagwagt.community.api.domain.post.entities.PostLike;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.common.enums.PostStatus;
import wagwagt.community.api.domain.post.interfaces.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager em;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public List<Post> findAll(int page , int size){
        return em.createQuery("select p from Post p where p.postStatus = :postStatus order by p.id desc",Post.class)
                .setParameter("postStatus",PostStatus.PUBLISHED)
                .setFirstResult((page - 1)*size)
                .setMaxResults(size)
                .getResultList();
    }
    public long findPostCounts(){
        return em.createQuery("select count(p) from Post p", Long.class)
                .getSingleResult();
    }

    public Post findById(Long id ){
        return em.find(Post.class,id);
    }

    public void postLike(PostLike postLike){
        em.persist(postLike);
    }

    @Override
    public Optional<Post> findTempByUser(User user) {
        List<Post> posts = em.createQuery("select p from Post p where p.postStatus = :postStatus and p.user = :user order by p.id desc", Post.class)
                .setParameter("postStatus", PostStatus.DRAFT)
                .setParameter("user", user)
                .setMaxResults(1)
                .getResultList();
        return posts.stream().findFirst();
    }

    @Override
    public void delete(Post post) {
        if(post != null){
            em.remove(post);
        }
    }

//    public boolean likeDupleCheck(PostLikeId postLikeId){
//        PostLike postLike = em.find(PostLike.class,postLikeId);
//
//        return postLike == null ? true : false;
//    }
}
