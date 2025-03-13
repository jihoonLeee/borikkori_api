package borikkori.community.api.domain.user.service.impl;

import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.domain.user.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository; // JPA Repository for UserEntity

    public User registerNewUser(String name, String email, String rawPassword) {
        // 서비스 레이어에서 비밀번호 인코딩 처리
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // 도메인 객체 생성 (인코딩된 비밀번호 전달)
        User user = User.create(name, email, encodedPassword);
        userRepository.save(user);
        return user;
    }

    public void duplicateUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일");
        }
    }
}
