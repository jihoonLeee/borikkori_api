package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.application.port.JwtServicePort;
import borikkori.community.api.common.exeptions.UserNotFoundException;
import borikkori.community.api.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.redis.RefreshTokenServiceAdapter;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.adapter.in.web.user.response.UserResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthenticationUsecaseImpl implements UserAuthenticationUsecase {

    private final UserRepository userRepository;
    private final JwtServicePort jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenServiceAdapter refreshTokenServiceAdapter;
    private final UserMapper userMapper;
    /**
     * 로그인: 이메일, 비밀번호 검증 후 JWT 토큰 생성, 쿠키 설정, 리프레시 토큰 저장 처리
     */
    @Transactional
    @Override
    public UserResponse login(LoginRequest req, HttpServletResponse response) {
        // 1. 이메일로 사용자 조회 (없으면 BadCredentialsException)
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정 정보"));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호");
        }

        // 3. JWT 토큰 생성 (사용자 권한 정보(user.getAuth())를 활용)
        String accessToken = jwtService.createToken(user.getEmail(), user.getName(), user.getRoles());
        String refreshToken = jwtService.createRefreshToken(user.getEmail());

        // 4. JWT 액세스 토큰을 HTTP 응답 쿠키에 설정 (보안, 도메인 등은 환경에 맞게 조정)
        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .path("/")
                .sameSite("Strict")
                .httpOnly(true)
                .secure(false)      // 실제 운영 시 secure(true)로 설정 필요
                .domain("example.com")  // 실제 도메인으로 변경
                .maxAge(60 * 30)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        // 5. 리프레시 토큰 정보를 저장 (Redis 등 사용)
        refreshTokenServiceAdapter.saveTokenInfo(user.getId().getId(), refreshToken, accessToken);

        // 6. 사용자 응답 DTO 반환 (필요한 정보만 포함)
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
