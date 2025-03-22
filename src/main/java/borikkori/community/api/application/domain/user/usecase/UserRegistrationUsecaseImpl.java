package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import borikkori.community.api.domain.mbti.repository.MbtiRepository;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.repository.UserRoleRepository;
import borikkori.community.api.domain.user.service.UserRegistrationService;
import borikkori.community.api.domain.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegistrationUsecaseImpl implements UserRegistrationUsecase {

    private final UserRegistrationService  userRegistrationService;
    private final UserRoleService userRoleService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MbtiRepository mbtiRepository;

    @Transactional
    @Override
    public Long joinUser(JoinRequest req, Optional<MbtiType> mbtiTypeOpt) {
        // 중복 체크 및 User 객체 생성 (아직 영속화 X)
        userRegistrationService.duplicateUser(req.getEmail());
        User user = userRegistrationService.registerNewUser(req.getName(), req.getEmail(), req.getPassword());

        // User 엔티티 영속화
        User savedUser = userRepository.saveUser(user);

        UserRole userRole = userRoleService.registerUserRole(savedUser,Role.USER,RoleStatus.ACTIVE);
        userRoleRepository.saveUserRole(userRole);

        UserResponse userResponse =  userMapper.toResponse(user);
        return userResponse.getId();
    }

}
