package wagwagt.community.api.domain.user.usecases;

import jakarta.servlet.http.HttpServletResponse;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.user.interfaces.dto.request.JoinRequest;
import wagwagt.community.api.domain.user.interfaces.dto.request.LoginRequest;
import wagwagt.community.api.domain.user.interfaces.dto.response.LoginResponse;

import java.util.Optional;

public interface UserUsecase {

    // 회원가입
    Long join(JoinRequest req);
    
    //회원 조회
    User findOne(Long userId);
    Optional<User> findByEmail(String email);
    // 회원탈퇴
    
    //로그인
    LoginResponse login(LoginRequest user, HttpServletResponse response);

    //로그인 정보
     LoginResponse loginInfo (String token) throws Exception;

    //비밀번호 변경
    
    // 비밀번호 찾기
}
