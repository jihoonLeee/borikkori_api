package wagwagt.community.api.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

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
