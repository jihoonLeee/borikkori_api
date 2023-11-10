package wagwagt.community.api.usecases;

import wagwagt.community.api.entities.User;
import wagwagt.community.api.requests.LoginRequest;
import wagwagt.community.api.responses.LoginResponse;

public interface UserUsecase {

    // 회원가입
    Long join(User user);
    
    //회원 조회
    User findOne(Long userId);
    
    // 회원탈퇴
    
    //로그인
    LoginResponse login(LoginRequest user);

    // 로그아웃

    String logout(User user);
    //비밀번호 변경
    
    // 비밀번호 찾기
}
