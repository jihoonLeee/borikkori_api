/*
package wagwagt.community.api.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.user.entities.enums.Role;
import wagwagt.community.api.domain.user.interfaces.dto.request.JoinRequest;
import wagwagt.community.api.domain.user.usecases.EmailVerificationUsecase;
import wagwagt.community.api.domain.user.usecases.UserUsecase;
import wagwagt.community.api.infrastructures.provider.JwtTokenProvider;
import wagwagt.community.api.domain.user.interfaces.repositories.UserRepository;

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


    */
/**
     * TODO : shouldSuccessJoinWithEmailVerification 과 같이 무엇을 위한 테스트인지 확실하게 이름을 정할 것
     * @throws Exception
     *//*

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
        Long id = userUsecase.join(req);


        //then
        Assertions.assertEquals(Role.USER,userRepository.findById(id).getAuth().get(0).getRole());
    }

    @Test

    public void login() throws Exception{
//        String passwd = "1234";
//        String  encodePasswd = passwordEncoder.encode("1234");
//        //given
//        Authority auth = Authority.builder().role(Role.DEFAULT).build();
//        User joinuser = User.builder()
//                .name("이지훈")
//                .password(encodePasswd)
//                .auth(auth)
//                .email("jihoon2723@naver.co")
//                .build();
//        JoinRequest req = new JoinRequest("이지훈","jihoon2723@naver.com",encodePasswd,);
//        //when
//        Long id = userUsecase.join(joinuser);
//        System.out.println("조인 성공 :  "+id);
//
//        LoginRequest req =  new LoginRequest();
//        req.setEmail("jihoon2723@naver.com");
//        req.setPassword(passwd);
//        HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
//
//        // addCookie 메소드가 호출되었을 때 쿠키를 받아와 토큰 값을 뽑아내는 로직 추가
//        Mockito.doAnswer(invocation -> {
//            Cookie cookie = invocation.getArgument(0, Cookie.class);
//            String token = cookie.getValue();
//            System.out.println(token + " 토큰");
//
//            //then
//            // 발급된 JWT 토큰을 검증
//            boolean isValidToken = jwtTokenProvider.validateToken(token);
//
//            // JWT 토큰의 유효성을 확인
//            Assertions.assertTrue(isValidToken);
//
//            return null;
//        }).when(res).addCookie(Mockito.any(Cookie.class));
//
//        userUsecase.login(req,res);
    }

}*/
