package borikkori.community.api.adapter.in.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;
import borikkori.community.api.application.port.RefreshTokenServicePort;
import borikkori.community.api.config.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Description("JWT 유효성 검증 및 자동 갱신 필터")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenServicePort refreshTokenServicePort;

	@Value("${cookie.domain}")
	private String cookieDomain;
	@Value("${cookie.path}")
	private String cookiePath;
	@Value("${cookie.secure}")
	private boolean cookieSecure;
	/** access token 만료 시간(초) */
	@Value("${jwt.secret.expiration-minutes}")
	private int accessTokenMaxAge;
	/** refresh token 만료 시간(초) */
	@Value("${jwt.secret.refresh-expiration-minutes}")
	private int refreshTokenMaxAge;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {
		try {
			// 1) access token이 없거나 유효하지 않으면 리프레시 시도
			String accessToken = jwtTokenProvider.resolveToken(request);
			if (accessToken == null || !jwtTokenProvider.validateToken(accessToken)) {
				attemptRefresh(request, response);
			}
			// 2) (원래 access or 새로 발급된) 토큰을 꺼내 인증 컨텍스트 설정
			String token = jwtTokenProvider.resolveToken(request);
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception ex) {
			log.error("Authentication error", ex);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
			return;
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * refresh_token 쿠키를 보고 access_token을 갱신하고, 필요시 refresh_token 회전
	 */
	private void attemptRefresh(HttpServletRequest request, HttpServletResponse response) {
		// 1) 쿠키에서 refresh_token 읽기
		String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
			.filter(c -> "refresh_token".equals(c.getName()))
			.map(Cookie::getValue)
			.findFirst().orElse(null);
		if (refreshToken == null) {
			return;
		}
		// 2) Redis에서 리프레시 토큰 조회
		Optional<RefreshTokenEntity> optional = refreshTokenServicePort.findByRefreshToken(refreshToken);
		if (optional.isEmpty()) {
			return;
		}
		RefreshTokenEntity entity = optional.get();
		// 3) 리프레시 토큰 유효성 검사
		if (!jwtTokenProvider.validateToken(entity.getRefreshToken())) {
			return;
		}
		// 4) 새 access_token 발급
		Claims info = jwtTokenProvider.getInfo(entity.getRefreshToken());
		String newAccess = jwtTokenProvider.createToken(
			info.getSubject(),
			info.get("name", String.class),
			info.get("role", List.class)
		);
		setCookie(response, "access_token", newAccess, accessTokenMaxAge);
		// 5) SecurityContext에도 새 인증 기입
		Authentication auth = jwtTokenProvider.getAuthentication(newAccess);
		SecurityContextHolder.getContext().setAuthentication(auth);
		// 6) refresh_token 회전(선택)
		String newRefresh = jwtTokenProvider.createRefreshToken(info.getSubject());
		entity.setRefreshToken(newRefresh);
		refreshTokenServicePort.saveTokenInfo(entity.getUserId(), newRefresh, newAccess);
		setCookie(response, "refresh_token", newRefresh, refreshTokenMaxAge);
	}

	/**
	 * 쿠키 생성 헬퍼
	 */
	private void setCookie(HttpServletResponse response,
		String name,
		String value,
		int maxAge) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.domain(cookieDomain)
			.path(cookiePath)
			.maxAge(Duration.ofMinutes(maxAge))
			.secure(cookieSecure)
			.httpOnly(true)
			.sameSite("None")
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}
}
