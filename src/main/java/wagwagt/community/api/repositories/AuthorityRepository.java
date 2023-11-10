package wagwagt.community.api.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.entities.User;

@Repository
@RequiredArgsConstructor
public class AuthorityRepository {

    private final EntityManager em;

    public void save(Authority auth){
        em.persist(auth);
    }

    public Authority findOne(Long id){
        return em.find(Authority.class,id);
    }

}
