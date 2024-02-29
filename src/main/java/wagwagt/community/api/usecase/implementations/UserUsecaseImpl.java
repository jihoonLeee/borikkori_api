package wagwagt.community.api.usecase.implementations;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.interfaces.controller.dto.RefreshToken;
import wagwagt.community.api.entities.domain.Authority;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.infrastructures.provider.JwtTokenProvider;
import wagwagt.community.api.interfaces.controller.repositories.AuthorityRepository;
import wagwagt.community.api.interfaces.controller.repositories.UserRepository;
import wagwagt.community.api.interfaces.controller.repositories.RefreshTokenRepository;
import wagwagt.community.api.interfaces.controller.dto.requests.JoinRequest;
import wagwagt.community.api.interfaces.controller.dto.requests.LoginRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.LoginResponse;
import wagwagt.community.api.usecase.UserUsecase;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserUsecaseImpl implements UserUsecase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원가입
     * */
    @Transactional
    @Override
    public Long join(JoinRequest req){
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .build();
        Authority auth = Authority.builder()
                .role(req.getRole())
                .build();
        user.setAuth(auth);
        duplicateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 회원조회
     * */
    @Override
    public User findOne(Long userId){
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /***
     * 로그인
     * @param req
     * @return
     */
    @Override
    public LoginResponse login(LoginRequest req, HttpServletResponse response){

        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정 정보")
        );
        if(!passwordEncoder.matches(req.getPassword(),user.getPassword()))
        {
            throw  new BadCredentialsException("잘못된 비밀번호");
        }
        String accessToken= jwtTokenProvider.createToken(user.getEmail(),user.getName(),authorityRepository.findOne(user.getId()));
        String refreshToken=jwtTokenProvider.createRefreshToken(user.getEmail());
        //쿠키 저장
         ResponseCookie cookie = ResponseCookie.from("access_token",accessToken)
                 .path("/")
                 .sameSite("false")
                 .httpOnly(true)
                 .secure(false)
                 .domain("localhost")
                 .maxAge(60*30)
                 .build();
         response.addHeader("Set-Cookie",cookie.toString());
        refreshTokenRepository.save(new RefreshToken(user.getId(),refreshToken,accessToken));

        return LoginResponse.builder()
                .nickName(user.getName())
                .role(user.getAuth().getRole())
                .build();
    }


    @Override
    public LoginResponse loginInfo (String token) throws Exception {
        Claims claims = jwtTokenProvider.getInfo(token);
        String userEmail = claims.getSubject();

        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return LoginResponse.builder()
                    .nickName(user.getName())
                    .email(user.getEmail())
                    .role(user.getAuth().getRole())
                    .build();
        } else {
            throw new Exception();
        }
    }

    private void duplicateUser(User user){
        Optional<User> find = userRepository.findByEmail(user.getEmail());
        if(!find.isEmpty()) throw new IllegalStateException("이미 존재하는 회원");
//        if(ObjectUtils.isEmpty(find)) throw new IllegalStateException("이미 존재하는 회원");
    }

}
