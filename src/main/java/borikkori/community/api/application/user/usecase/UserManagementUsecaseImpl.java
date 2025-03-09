package borikkori.community.api.application.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.common.exeptions.NotExistAuthException;
import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.domain.user.repository.AuthorityRepository;
import borikkori.community.api.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserManagementUsecaseImpl implements UserManagementUsecase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    @Transactional
    @Override
    public Long join(JoinRequest req) {
        duplicateUser(req.getEmail());
        String encodedPw = passwordEncoder.encode(req.getPassword());

        RoleEntity auth = authorityRepository.findByRole(req.getRole())
                .orElseThrow(() -> new NotExistAuthException(req.getRole() + " 권한이 존재하지 않습니다."));

        UserEntity user = UserEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(encodedPw)
                .build();
        user.setAuth(List.of(auth));

        userRepository.save(user);
        return user.getId();
    }


    /**
     * 회원조회
     * */
    @Override
    public Optional<UserEntity> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void duplicateUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일");
        }
    }

}
