package borikkori.community.api.domain.user.service;

import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.entity.User;

public interface UserRoleService {
    void registerUserRole(User user, Role role, RoleStatus status);
}
