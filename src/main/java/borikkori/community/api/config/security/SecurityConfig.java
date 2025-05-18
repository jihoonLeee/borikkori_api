package borikkori.community.api.config.security;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
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
import borikkori.community.api.application.port.RefreshTokenServicePort;
import borikkori.community.api.common.enums.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity  // Spring Security 활성화
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenServicePort refreshTokenServicePort;
	@Value("${cors.allowed.origins}")
	private String allowedOrigins;

	@Value("${cookie.domain}")
	private String cookieDomain;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider,
			refreshTokenServicePort);
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
				.addLogoutHandler((req, res, auth) -> {
					String refreshToken = Arrays.stream(Optional.ofNullable(req.getCookies()).orElse(new Cookie[0]))
						.filter(c -> "refresh_token".equals(c.getName()))
						.map(Cookie::getValue)
						.findFirst().orElse(null);

					if (refreshToken != null) {
						// 2) Redis에서 제거
						refreshTokenServicePort.removeRefreshToken(refreshToken);
					}
					// 두 쿠키 모두 삭제
					addDeleteCookie(res, "access_token");
					addDeleteCookie(res, "refresh_token");
				})
				.logoutSuccessHandler((req, res, auth) ->
					res.setStatus(HttpServletResponse.SC_NO_CONTENT)))
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

	private void addDeleteCookie(HttpServletResponse res, String name) {
		ResponseCookie cookie = ResponseCookie.from(name, "")
			.domain(cookieDomain)
			.path("/")
			.sameSite("None")
			.httpOnly(true)
			.secure(true)
			.maxAge(0)
			.build();
		res.addHeader("Set-Cookie", cookie.toString());
	}

}
