package wagwagt.community.api.infrastructures.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wagwagt.community.api.domain.user.entities.enums.Role;
import wagwagt.community.api.infrastructures.provider.JwtTokenProvider;
import wagwagt.community.api.domain.user.interfaces.repositories.RefreshTokenRepository;

@Configuration
@EnableWebSecurity  //spring security 활성화
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider,refreshTokenRepository);

        return http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())  // csfr 잠시 사용 안함
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**","/mbti","/file/**","/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/user/join","/user/login","/user/sendEmail").permitAll()
                        .requestMatchers(HttpMethod.GET,"/posts/**","/comment/**","/ws/chat/**","/chat/**").permitAll()
                        .anyRequest().hasAnyAuthority(Role.USER.getRole(),Role.ADMIN.getRole())
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
