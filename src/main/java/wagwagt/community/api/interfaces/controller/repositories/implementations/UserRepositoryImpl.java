package wagwagt.community.api.interfaces.controller.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User findById(Long id ){
        return em.find(User.class,id);
    }

    public Optional<User> findByEmail(String email){
        List<User> users = em.createQuery("select u from User u where u.email =:email", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.ofNullable(users.get(0));
    }

    //유저 삭제
    public void delete(User user){

    }

}
