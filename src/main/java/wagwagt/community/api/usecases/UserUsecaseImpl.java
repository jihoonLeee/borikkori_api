package wagwagt.community.api.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import wagwagt.community.api.entities.User;
//import wagwagt.community.api.repositories.EmailVerificationRepository;
import wagwagt.community.api.infrastructures.security.JwtTokenProvider;
import wagwagt.community.api.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserUsecaseImpl implements UserUsecase{

    private final UserRepository userRepository;
//    private final EmailVerificationUsecase emailVerificationUsecase;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원가입
     * */
    @Transactional
    @Override
    public Long join(User user){
        duplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 회원조회
     * */
    @Override
    public User findOne(Long userId){
        return userRepository.findOne(userId);
    }



    private void duplicateUser(User user){
        Optional<User> find = userRepository.findByEmail(user.getEmail());
        if(!find.isEmpty()) throw new IllegalStateException("이미 존재하는 회원");
//        if(ObjectUtils.isEmpty(find)) throw new IllegalStateException("이미 존재하는 회원");
    }

    public String login(User user){
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정 정보")
        );
        if(!passwordEncoder.matches(user.getPassword(),user1.getPassword()))
        {
            throw  new BadCredentialsException("잘못된 비밀번호");
        }
        return "OK";
    }
}
