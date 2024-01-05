package wagwagt.community.api.usecases;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import wagwagt.community.api.dto.RefreshToken;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.entities.User;
//import wagwagt.community.api.repositories.EmailVerificationRepository;
import wagwagt.community.api.infrastructures.security.JwtTokenProvider;
import wagwagt.community.api.repositories.AuthorityRepository;
import wagwagt.community.api.repositories.RefreshTokenRepository;
import wagwagt.community.api.repositories.UserRepository;
import wagwagt.community.api.requests.LoginRequest;
import wagwagt.community.api.responses.LoginResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserUsecaseImpl implements UserUsecase{

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
    public Long join(User user){
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



    private void duplicateUser(User user){
        Optional<User> find = userRepository.findByEmail(user.getEmail());
        if(!find.isEmpty()) throw new IllegalStateException("이미 존재하는 회원");
//        if(ObjectUtils.isEmpty(find)) throw new IllegalStateException("이미 존재하는 회원");
    }

    /***
     * 로그인
     * @param loginReq
     * @return
     */
    public LoginResponse login(LoginRequest loginReq , HttpServletResponse response){

        User user = userRepository.findByEmail(loginReq.getEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정 정보")
        );
        if(!passwordEncoder.matches(loginReq.getPassword(),user.getPassword()))
        {
            throw  new BadCredentialsException("잘못된 비밀번호");
        }
        String accessToken= jwtTokenProvider.createToken(user.getEmail(),authorityRepository.findOne(user.getId()));
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
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public LoginResponse loginInfo (String token) throws Exception {
        Claims claims = jwtTokenProvider.getInfo(token);
        String userEmail = claims.getSubject();

        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return LoginResponse.builder()
                    .nickName(user.getName())
                    .role(user.getAuth().getRole())
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            throw new Exception();
        }
    }

}
