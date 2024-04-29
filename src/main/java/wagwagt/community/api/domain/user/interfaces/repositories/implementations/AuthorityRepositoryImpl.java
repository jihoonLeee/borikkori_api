package wagwagt.community.api.domain.user.interfaces.repositories.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.domain.user.entities.Authority;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.user.entities.enums.Role;
import wagwagt.community.api.domain.user.interfaces.repositories.AuthorityRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Authority> findByRole(Role role){
        List<Authority> auth = em.createQuery("select a from Authority a where a.role = :role", Authority.class)
                .setParameter("role", role)
                .getResultList();
        return auth.isEmpty() ? Optional.empty() : Optional.ofNullable(auth.get(0));
    }


}