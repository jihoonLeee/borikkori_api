package borikkori.community.api.application.domain.user.usecase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.adapter.out.redis.RefreshTokenServiceAdapter;
import borikkori.community.api.application.port.JwtServicePort;
import borikkori.community.api.common.exeptions.UserNotFoundException;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthenticationUsecaseImpl implements UserAuthenticationUsecase {

	@Value("${cookie.domain}")
	private String cookieDomain;
	@Value("${cookie.secure}")
	private boolean cookieSecure;
	private final UserRepository userRepository;
	private final JwtServicePort jwtService;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenServiceAdapter refreshTokenServiceAdapter;
	private final UserMapper userMapper;

	@Override
	public UserResponse login(LoginRequest req, HttpServletResponse response) {
		// 1. 이메일로 사용자 조회
		User user = userRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new BadCredentialsException("잘못된 계정 정보"));

		// 2. 비밀번호 검증
		if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("잘못된 비밀번호");
		}

		// 3. JWT 토큰 생성
		String accessToken = jwtService.createToken(user.getEmail(), user.getName(), user.getRoles());
		String refreshToken = jwtService.createRefreshToken(user.getEmail());

		// 4. Access Token 쿠키 설정
		ResponseCookie accessCookie = ResponseCookie.from("access_token", accessToken)
			.path("/")
			.domain(cookieDomain)
			.httpOnly(true)
			.secure(cookieSecure)
			.sameSite("None")
			.maxAge(60 * 30)             // 30분
			.build();
		response.addHeader("Set-Cookie", accessCookie.toString());

		// 5. Refresh Token 쿠키 설정 (추가)
		ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
			.path("/")
			.domain(cookieDomain)
			.httpOnly(true)
			.secure(cookieSecure)
			.sameSite("None")
			.maxAge(60 * 60 * 24 * 7)    // 예: 7일
			.build();
		response.addHeader("Set-Cookie", refreshCookie.toString());

		// 6. 리프레시 토큰 정보는 서버측 저장소에도 남겨 놓기
		refreshTokenServiceAdapter.saveTokenInfo(
			user.getId().getId(), refreshToken, accessToken);

		// 7. 사용자 응답 DTO 반환
		return UserResponse.builder()
			.name(user.getName())
			.email(user.getEmail())
			.build();
	}

	/**
	 * 토큰으로부터 사용자 정보를 조회하여 응답 DTO로 변환
	 */
	@Override
	public UserResponse loginInfo(String token) {
		Claims claims = jwtService.getInfo(token);
		String userEmail = claims.getSubject();

		User user = userRepository.findByEmail(userEmail)
			.orElseThrow(() -> new UserNotFoundException("유저 정보 없음"));

		return userMapper.toResponse(user);
	}
}
