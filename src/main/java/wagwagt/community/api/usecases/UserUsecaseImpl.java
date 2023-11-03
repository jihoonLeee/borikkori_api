package wagwagt.community.api.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.entities.User;
//import wagwagt.community.api.repositories.EmailVerificationRepository;
import wagwagt.community.api.repositories.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserUsecaseImpl implements UserUsecase{

    private final UserRepository userRepository;
//    private final EmailVerificationUsecase emailVerificationUsecase;
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
       List<User> list = userRepository.findByEmail(user.getEmail()).stream().toList();
       if(!list.isEmpty()) throw new IllegalStateException("이미 존재하는 회원");
    }
}
