package wagwagt.community.api.infrastructures.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import wagwagt.community.api.repositories.AuthorityRepository;
import wagwagt.community.api.repositories.RefreshTokenRepository;
import wagwagt.community.api.repositories.UserRepository;

import java.util.Arrays;
@Configuration
@EnableWebSecurity  //spring security 활성화
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final String[] allowsUrls = {"/","/users/**","/swagger-ui/**","/logout"};
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider,refreshTokenRepository);

        return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.ignoringRequestMatchers(allowsUrls)) // CSRF 검증을 건너뛰는 경로 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(allowsUrls).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/users/logout")
                        .deleteCookies("access_token")
                        .permitAll())
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
