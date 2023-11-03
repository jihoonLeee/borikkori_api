package wagwagt.community.api.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class,id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u",User.class).getResultList();
    }

    public List<User> findByEmail(String email){
        return em.createQuery("select u from User u where u.email =:email",User.class)
                .setParameter("email",email)
                .getResultList();
    }

    public void leave(User user){

    }

}
