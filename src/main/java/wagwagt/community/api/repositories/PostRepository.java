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

    public List<Post> findAll(){
        return em.createQuery("select p from post p",Post.class).getResultList();
    }

    public Post findById(Long id ){
        return em.find(Post.class,id);
    }

    public Post findByUser(User user){
        return em.find(Post.class,user);
    }
}
