package borikkori.community.api.application.user.usecase;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.domain.user.service.JwtService;
import borikkori.community.api.domain.user.service.RefreshTokenService;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.adapter.in.web.user.response.LoginResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthUsecaseImpl implements UserAuthUsecase {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    /***
     * 로그인
     * @param req
     * @return
     */
    @Transactional
    @Override
    public LoginResponse login(LoginRequest req, HttpServletResponse response) {
        UserEntity user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정 정보")
        );
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호");
        }

        String accessToken = jwtService.createToken(user.getEmail(), user.getName(), user.getAuth());
        String refreshToken = jwtService.createRefreshToken(user.getEmail());

        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .path("/")
                .sameSite("Strict")
                .httpOnly(true)
                .secure(false)
                .domain("example.com")
                .maxAge(60 * 30)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        refreshTokenService.saveTokenInfo(user.getId(), refreshToken, accessToken);

        return LoginResponse.builder()
                .nickName(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public LoginResponse loginInfo(String token) throws Exception {
        Claims claims = jwtService.getInfo(token);
        String userEmail = claims.getSubject();

        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(() -> new Exception("유저 정보 없음"));

        return LoginResponse.builder()
                .nickName(user.getName())
                .email(user.getEmail())
                .mbtiType(user.getMbtiEntity() != null ? user.getMbtiEntity().getResult() : null)
                .role(user.getAuth().get(0).getRole())
                .build();
    }

}
