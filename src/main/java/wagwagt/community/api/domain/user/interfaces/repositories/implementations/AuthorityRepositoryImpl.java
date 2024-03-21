package wagwagt.community.api.domain.user.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.domain.user.entities.Authority;
import wagwagt.community.api.domain.user.interfaces.repositories.AuthorityRepository;

@Repository
@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepository {

    private final EntityManager em;

    public void save(Authority auth){
        em.persist(auth);
    }

    public Authority findOne(Long id){
        return em.find(Authority.class,id);
    }

}
