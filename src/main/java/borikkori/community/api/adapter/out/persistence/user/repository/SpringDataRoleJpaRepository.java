package borikkori.community.api.adapter.out.persistence.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;

public interface SpringDataRoleJpaRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByRole(Role role);
}
