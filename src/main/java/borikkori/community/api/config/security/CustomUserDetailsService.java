package borikkori.community.api.config.security;

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user =  userRepository.findByEmail(email).orElseThrow(
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
