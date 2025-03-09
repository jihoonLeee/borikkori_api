package borikkori.community.api.domain.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;

import java.util.Optional;

public interface AuthorityRepository {
    void save(RoleEntity auth);

    RoleEntity findOne(Long id);

    Optional<RoleEntity> findByRole(Role role);

    long count() ;
}
