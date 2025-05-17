package borikkori.community.api.adapter.in.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;
import borikkori.community.api.application.port.RefreshTokenServicePort;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.config.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Description("JWT 유효성 검증 필터")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenServicePort refreshTokenServicePort;

	@Value("${cookie.domain}")
	private String cookieDomain;

	@Value("${cookie.secure}")
	private boolean cookieSecure;

	@Value("${cookie.path}")
	private String cookiePath;

	@Value("${cookie.max-age}")
	private int cookieMaxAge;

	@Override
	protected void doFilterInternal(HttpServletRequest req,
		HttpServletResponse res,
		FilterChain chain) throws ServletException, IOException {
		try {
			String accessToken = jwtTokenProvider.resolveToken(req);
			if (accessToken != null) {
				if (jwtTokenProvider.validateToken(accessToken)) {
					SecurityContextHolder.getContext()
						.setAuthentication(jwtTokenProvider.getAuthentication(accessToken));
				} else {
					attemptRefresh(req, res);
				}
			}
		} catch (Exception e) {
			log.error("Authentication failed", e);
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
			return;
		}
		chain.doFilter(req, res);
	}

	private void attemptRefresh(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 1) 쿠키에서 리프레시 토큰 꺼내기
		String refreshToken = Arrays.stream(Optional.ofNullable(req.getCookies()).orElse(new Cookie[0]))
			.filter(c -> "refresh_token".equals(c.getName()))
			.map(Cookie::getValue)
			.findFirst().orElse(null);

		if (refreshToken == null)
			return;

		// 2) 레디스에서 조회
		RefreshTokenEntity entity = refreshTokenServicePort
			.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

		// 3) 유효성 검사
		if (!jwtTokenProvider.validateToken(entity.getRefreshToken()))
			return;

		// 4) 새 액세스 토큰
		Claims info = jwtTokenProvider.getInfo(entity.getRefreshToken());
		String newAccess = jwtTokenProvider.createToken(
			info.getSubject(),
			(String)info.get("name"),
			(List<Role>)info.get("role")
		);
		setCookie(res, "access_token", newAccess, cookieMaxAge);

		// 5) 시큐리티 컨텍스트에 설정
		SecurityContextHolder.getContext()
			.setAuthentication(jwtTokenProvider.getAuthentication(newAccess));

		// 6) 리프레시 토큰 회전
		String newRefresh = jwtTokenProvider.createRefreshToken(info.getSubject());
		entity.setRefreshToken(newRefresh);
		refreshTokenServicePort.saveTokenInfo(entity.getUserId(), newRefresh, newAccess);
		setCookie(res, "refresh_token", newRefresh, cookieMaxAge);
	}

	private void setCookie(HttpServletResponse res,
		String name,
		String value,
		int maxAgeSeconds) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.domain(cookieDomain)    // ex. ".bokko.kr"
			.path(cookiePath)        // ex. "/"
			.maxAge(maxAgeSeconds)
			.secure(cookieSecure)
			.httpOnly(true)
			.sameSite("None")
			.build();
		res.addHeader("Set-Cookie", cookie.toString());
	}
}
