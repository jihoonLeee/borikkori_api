package borikkori.community.api.domain.user.repository;

import java.util.Optional;

import borikkori.community.api.common.enums.Role;

public interface RoleRepository {
	void saveRole(Role role);

	Role findRoleById(Long id);

	Optional<Role> findByRole(Role role);

	long countRole();
}
