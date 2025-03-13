package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.service.UserRegistrationService;
import borikkori.community.api.domain.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegistrationUsecaseImpl implements UserRegistrationUsecase {

    private final UserRegistrationService  userRegistrationService;
    private final UserRoleService userRoleService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long joinUser(JoinRequest req) {
        userRegistrationService.duplicateUser(req.getEmail());
        User user = userRegistrationService.registerNewUser(req.getName(), req.getEmail(), req.getPassword());
        userRoleService.registerUserRole(user,Role.USER,RoleStatus.ACTIVE);
        UserResponse userResponse =  userMapper.toResponse(user);
        return userResponse.getId();
    }

}
