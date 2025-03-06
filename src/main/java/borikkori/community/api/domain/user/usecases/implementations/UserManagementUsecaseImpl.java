package borikkori.community.api.domain.user.usecases.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.common.exeptions.NotExistAuthException;
import borikkori.community.api.domain.user.entities.Authority;
import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.domain.user.interfaces.dto.request.JoinRequest;
import borikkori.community.api.domain.user.interfaces.repositories.AuthorityRepository;
import borikkori.community.api.domain.user.interfaces.repositories.UserRepository;
import borikkori.community.api.domain.user.usecases.UserManagementUsecase;

import java.util.Arrays;
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

        Authority auth = authorityRepository.findByRole(req.getRole())
                .orElseThrow(() -> new NotExistAuthException(req.getRole() + " 권한이 존재하지 않습니다."));

        User user = User.builder()
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
    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void duplicateUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일");
        }
    }

}
