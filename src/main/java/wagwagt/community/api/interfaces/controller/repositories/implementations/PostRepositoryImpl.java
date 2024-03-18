package wagwagt.community.api.interfaces.controller.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.domain.*;
import wagwagt.community.api.entities.domain.enums.PostStatus;
import wagwagt.community.api.interfaces.controller.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public List<Post> findAll(int page , int size){
        return em.createQuery("select p from Post p order by p.id desc",Post.class)
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
