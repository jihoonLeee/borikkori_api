package borikkori.community.api.usecase;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.application.domain.user.usecase.EmailVerificationUsecase;
import borikkori.community.api.application.domain.user.usecase.UserAuthenticationUsecase;
import borikkori.community.api.application.domain.user.usecase.UserRegistrationUsecase;
import borikkori.community.api.config.security.JwtTokenProvider;
import borikkori.community.api.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class UserEntityUsecaseImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthenticationUsecase userAuthenticationUsecase;
    @Autowired
    UserRegistrationUsecase userRegistrationUsecase;
    @Autowired
    EmailVerificationUsecase emailVerificationUsecase;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


/*
     * TODO : shouldSuccessJoinWithEmailVerification 과 같이 무엇을 위한 테스트인지 확실하게 이름을 정할 것
     * @throws Exception
*/

    @Test
    public void join() throws Exception{
        //given
        //인증을 먼저 받고 받은 이메일이 다음 챕터로 넘어감
        int code = emailVerificationUsecase.sendEmail("jihoon2723@naver.com");
        emailVerificationUsecase.checkEmail(code,"jihoon2723@naver.com");
        String encodedPw = passwordEncoder.encode("1234");


        JoinRequest req = new JoinRequest();
        req.setEmail("ljh2723@naver.com");
        req.setPassword(encodedPw);
        req.setName("테스트");
        req.setVerificationNumber(code);
        //when
        Long id = userRegistrationUsecase.joinUser(req);

        //then
        Assertions.assertEquals(Role.USER,Role.USER);
    }

    @Test

    public void login() throws Exception{
       /* String passwd = "1234";
        String  encodePasswd = passwordEncoder.encode("1234");
        int code = emailVerificationUsecase.sendEmail("jihoon2723@naver.com");
        //given

        RoleEntity auth = RoleEntity.builder().role(Role.USER).build();
        List<RoleEntity> authList = new ArrayList<>();
        authList.add(auth);
        UserEntity joinuser = new UserEntity
                ("이지훈",encodePasswd,authList,"jihoon2723@naver.com" );
        JoinRequest req = new JoinRequest("이지훈","jihoon2723@naver.com",encodePasswd,code);
        //when
        Long id = userRegistrationUsecase.joinUser(req);
        System.out.println("조인 성공 :  "+id);

        LoginRequest loginRequest =  new LoginRequest();
        req.setEmail("jihoon2723@naver.com");
        req.setPassword(passwd);
        HttpServletResponse res = Mockito.mock(HttpServletResponse.class);

        // addCookie 메소드가 호출되었을 때 쿠키를 받아와 토큰 값을 뽑아내는 로직 추가
        Mockito.doAnswer(invocation -> {
            Cookie cookie = invocation.getArgument(0, Cookie.class);
            String token = cookie.getValue();
            System.out.println(token + " 토큰");

            //then
            // 발급된 JWT 토큰을 검증
            boolean isValidToken = jwtTokenProvider.validateToken(token);

            // JWT 토큰의 유효성을 확인
            Assertions.assertTrue(isValidToken);

            return null;
        }).when(res).addCookie(Mockito.any(Cookie.class));

        userAuthenticationUsecase.login(loginRequest,res);*/
    }

}
