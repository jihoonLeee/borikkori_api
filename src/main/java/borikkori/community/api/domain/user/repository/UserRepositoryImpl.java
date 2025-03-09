package borikkori.community.api.domain.user.repository;

import borikkori.community.api.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    public void save(UserEntity user){
        em.persist(user);
    }

    public List<UserEntity> findAll(){
        return em.createQuery("select u from User u", UserEntity.class).getResultList();
    }

    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(em.find(UserEntity.class, id));
    }

    public Optional<UserEntity> findByEmail(String email){
        List<UserEntity> users = em.createQuery("select u from User u where u.email =:email", UserEntity.class)
                .setParameter("email", email)
                .getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.ofNullable(users.get(0));
    }

    //유저 삭제
    public void delete(User user){

    }

}
