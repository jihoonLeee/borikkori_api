package borikkori.community.api.config.security;

import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );
        return  new CustomUserDetails(user);
    }
//    public UserDetails createUserDetails(Member member){
//        return User.builder()
//                .username(member.getEmail())
//                .password(member.getPassword())
//                .authorities(member.getAuth().getAuthority())
//                .build();
//    }
}
