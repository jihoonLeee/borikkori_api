package borikkori.community.api.adapter.out.persistence.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataRoleJpaRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRole(Role role);
}
