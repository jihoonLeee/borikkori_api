package borikkori.community.api.domain.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;

import java.util.Optional;

public interface RoleRepository {
    void saveRole(Role role);

    Role findRoleById(Long id);

    Optional<Role> findByRole(Role role);

    long countRole() ;
}
