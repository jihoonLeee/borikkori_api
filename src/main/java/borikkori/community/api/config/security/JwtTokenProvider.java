package borikkori.community.api.config.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import borikkori.community.api.common.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
	@Value("${jwt.secret.key}")
	private String salt;

	private Key secretKey;

	@Value("${jwt.secret.expiration-minutes}")
	private long exp;

	@Value("${jwt.secret.refresh-expiration-minutes}")
	private long refreshExp;

	private final CustomUserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = Keys.hmacShaKeyFor(salt.getBytes());
	}

	/**
	 * 토큰 생성
	 */
	public String createToken(String email, String nickName, List<Role> roles) {
		// roles 가 null 이면 빈 리스트로 대체 - NPE 방지
		List<String> roleNames = Optional.ofNullable(roles)
			.orElseGet(Collections::emptyList)
			.stream()
			.map(Role::getRole)
			.collect(Collectors.toList());

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("role", roleNames);
		claims.put("name", nickName);

		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(Date.from(Instant.now().plus(exp, ChronoUnit.MINUTES)))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * 리프래시 토큰 생성
	 */
	public String createRefreshToken(String email) {
		Claims claims = Jwts.claims().setSubject(email);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(Date.from(Instant.now().plus(refreshExp, ChronoUnit.MINUTES)))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * 권한 정보 획득
	 */
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getAccount(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/**
	 * 토큰에 담겨있는 유저 account 획득
	 */
	public String getAccount(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * 토큰에 담겨있는 body(claims)정보 획득
	 */
	public Claims getInfo(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	// JwtTokenProvider.java
	public String resolveToken(HttpServletRequest request) {
		// 1) Authorization 헤더
		String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		// 2) 없으면 Cookie에서
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("access_token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 토큰 검증
	 * */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못됨");
		}
		return false;
	}

}
