package borikkori.community.api.config.security;

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

import borikkori.community.api.adapter.in.filter.JwtAuthenticationFilter;
import borikkori.community.api.adapter.out.redis.repository.RefreshTokenRepository;
import borikkori.community.api.common.enums.Role;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity  // Spring Security 활성화
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider,
			refreshTokenRepository);
		return http.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
				// 모든 사용자에게 허용되는 경로
				.requestMatchers("/mbti/**", "/videos/**", "/images/**", "/documents/**")
				.permitAll()
				// POST 요청에 대해 허용되는 경로
				.requestMatchers(HttpMethod.POST, "/user/join", "/user/login", "/user/sendEmail")
				.permitAll()
				// 모든 사용자에게 허용되는 경로 (GET)
				.requestMatchers(HttpMethod.GET, "/post/**", "/comment/**")
				.permitAll()
				// 인증된 사용자에게만 허용되는 나머지 GET 경로
				.requestMatchers(HttpMethod.GET, "/ws/chat/**", "/chat/**", "/swagger-ui/**")
				.authenticated()
				// 나머지 모든 요청에 대해서는 USER 또는 ADMIN 권한이 필요
				.anyRequest()
				.hasAnyAuthority(Role.USER.getRole(), Role.ADMIN.getRole())
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/users/logout")
				.deleteCookies("access_token")
				.permitAll())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			//rememberMe(Customizer.withDefaults())
			.build();
	}

	/**
	 * 암호화 모듈
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
