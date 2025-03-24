package borikkori.community.api.domain.user.service;

import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;

public interface UserRoleService {
	UserRole registerUserRole(User user, Role role, RoleStatus status);
}
