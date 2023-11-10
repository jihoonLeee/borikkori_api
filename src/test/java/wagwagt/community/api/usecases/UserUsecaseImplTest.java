package wagwagt.community.api.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.controllers.UserController;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.enums.Role;
import wagwagt.community.api.infrastructures.security.JwtTokenProvider;
import wagwagt.community.api.repositories.UserRepository;
import wagwagt.community.api.requests.LoginRequest;
import wagwagt.community.api.responses.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserUsecaseImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserUsecase userUsecase;
    @Autowired
    EmailVerificationUsecase emailVerificationUsecase;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Test
    public void 회원가입() throws Exception{
        //given
        //인증을 먼저 받고 받은 이메일이 다음 챕터로 넘어감
        int code = emailVerificationUsecase.sendEmail("jihoon2723@naver.com");
        emailVerificationUsecase.checkEmail(code,"jihoon2723@naver.com");
        User user = User.builder()
                .name("이지훈")
                .password("1234")
                .email("jihoon2723@naver.com")
                .build();
        
        //when
        Long id = userUsecase.join(user);
        System.out.println(id + " : id");
        //then
        Assertions.assertEquals(user,userRepository.findOne(id));
    }
    
    @Test
    public void 로그인() throws Exception{
        String passwd = "1234";
        String  encodePasswd = passwordEncoder.encode("1234");
        //given
        Authority auth = Authority.builder().role(Role.NORMAL).build();
        User joinuser = User.builder()
                .name("이지훈")
                .password(encodePasswd)
                .auth(auth)
                .email("jihoon2723@naver.com")
                .build();
        //when
        Long id = userUsecase.join(joinuser);
        System.out.println("조인 성공 :  "+id);

        LoginRequest req =  new LoginRequest();
        req.setEmail("jihoon2723@naver.com");
        req.setPassword(passwd);
       String token =  userUsecase.login(req).getAccessToken();

       Assertions.assertNotNull(token);
       System.out.println(token + " 토큰");
       //then
// 발급된 JWT 토큰을 검증
        boolean isValidToken = jwtTokenProvider.validateToken(token);

        // JWT 토큰의 유효성을 확인
        Assertions.assertTrue(isValidToken);
    }
}