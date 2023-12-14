package wagwagt.community.api.infrastructures.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wagwagt.community.api.repositories.AuthorityRepository;
import wagwagt.community.api.repositories.RefreshTokenRepository;
import wagwagt.community.api.repositories.UserRepository;

@Configuration
@EnableWebSecurity  //spring security 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final String[] allowsUrls = {"/","/users/**","/swagger-ui/**"};
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);

        return http.csrf(csrf -> csrf.ignoringRequestMatchers(allowsUrls)) // CSRF 검증을 건너뛰는 경로 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(allowsUrls).permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/view/users/login")
//                        .permitAll()
//                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .rememberMe(Customizer.withDefaults()).build();
    }


    /**
     * 암호화 모듈
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
