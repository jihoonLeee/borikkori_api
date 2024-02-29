package wagwagt.community.api.interfaces.controller.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.domain.Authority;
import wagwagt.community.api.interfaces.controller.repositories.AuthorityRepository;

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
