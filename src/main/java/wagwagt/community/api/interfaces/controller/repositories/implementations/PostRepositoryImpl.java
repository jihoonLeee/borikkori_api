package wagwagt.community.api.interfaces.controller.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.domain.Post;
import wagwagt.community.api.entities.domain.PostLike;
import wagwagt.community.api.entities.domain.PostLikeId;
import wagwagt.community.api.interfaces.controller.repositories.PostRepository;

import java.util.List;

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

    public boolean likeDupleCheck(PostLikeId postLikeId){
        PostLike postLike = em.find(PostLike.class,postLikeId);

        return postLike == null ? true : false;
    }
}
