package borikkori.community.api.domain.user.service.impl;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.repository.RoleRepository;
import borikkori.community.api.domain.user.repository.UserRoleRepository;
import borikkori.community.api.domain.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final RoleRepository roleRepository;

    public UserRole registerUserRole(User user, Role role, RoleStatus status) {
        Role findRole = roleRepository.findByRole(role)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Role"));
        return UserRole.create(user, findRole,status);
    }
}
