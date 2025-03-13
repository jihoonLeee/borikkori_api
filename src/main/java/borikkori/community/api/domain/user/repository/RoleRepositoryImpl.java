package borikkori.community.api.domain.user.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager em;

    public void save(RoleEntity auth){
        em.persist(auth);
    }

    public RoleEntity findOne(Long id){
        return em.find(RoleEntity.class,id);
    }

    @Override
    public Optional<RoleEntity> findByRole(Role role) {
        List<RoleEntity> auth = em.createQuery("select a from Authority a where a.role = :role", RoleEntity.class)
                .setParameter("role", role)
                .getResultList();
        return auth.stream().findFirst();
    }
    @Override
    public long count() {
        return em.createQuery("select count(a) from Authority a", Long.class)
                .getSingleResult();
    }

}
