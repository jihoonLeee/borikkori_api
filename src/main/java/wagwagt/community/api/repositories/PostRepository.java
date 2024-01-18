package wagwagt.community.api.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.Post;
import wagwagt.community.api.entities.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

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

    public Post findByUser(User user){
        return em.find(Post.class,user);
    }
}
