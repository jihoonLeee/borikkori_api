package borikkori.community.api.config.security;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.ForwardedHeaderFilter;

import borikkori.community.api.adapter.in.filter.JwtAuthenticationFilter;
import borikkori.community.api.adapter.out.redis.repository.RefreshTokenRepository;
import borikkori.community.api.common.enums.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity  // Spring Security 활성화
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	@Value("${cors.allowed.origins}")          // ← yml에서 주입
	private String allowedOrigins;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider,
			refreshTokenRepository);
		return http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
				.deleteCookies("access_token")
				.logoutSuccessHandler((req, res, auth) -> {
					res.setStatus(
						HttpServletResponse.SC_OK);
					res.setContentType("application/json");
					res.getWriter().print("{\"message\":\"로그아웃 성공\"}");
				})
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

	/* ===== CORS 세부 설정 ===== */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		Arrays.stream(allowedOrigins.split(","))
			.map(String::trim)
			.forEach(config::addAllowedOrigin);
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true); // 쿠키/Authorization 헤더 허용
		config.setMaxAge(Duration.ofHours(1));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

}
