package borikkori.community.api.domain.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;

import java.util.Optional;

public interface RoleRepository {
    void save(RoleEntity auth);

    RoleEntity findById(Long id);

    Optional<RoleEntity> findByRole(Role role);

    long count() ;
}
