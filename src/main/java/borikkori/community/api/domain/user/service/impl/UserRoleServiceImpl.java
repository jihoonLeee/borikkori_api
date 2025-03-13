package borikkori.community.api.domain.user.service.impl;

import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.repository.UserRoleRepository;
import borikkori.community.api.domain.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public void registerUserRole(User user, Role role, RoleStatus status) {
        UserRole userRole = UserRole.create(user, role,status);
        userRoleRepository.save(userRole);
    }
}
