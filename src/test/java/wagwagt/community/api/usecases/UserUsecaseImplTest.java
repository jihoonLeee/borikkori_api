package wagwagt.community.api.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.repositories.UserRepository;

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
        User joinuser = User.builder()
                .name("이지훈")
                .password(encodePasswd)
                .email("jihoon2723@naver.com")
                .build();
        //when
        Long id = userUsecase.join(joinuser);
        System.out.println("조인 성공 :  "+id);
        User loginuser = User.builder()
                .name("이지훈")
                .password(passwd)
                .email("jihoon2723@naver.com")
                .build();
       String result =  userUsecase.login(loginuser);
//        //then
        Assertions.assertEquals(result,"OK");
    }
}