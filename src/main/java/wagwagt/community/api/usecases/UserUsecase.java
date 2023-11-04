package wagwagt.community.api.usecases;

import wagwagt.community.api.entities.User;

public interface UserUsecase {

    // 회원가입
    Long join(User user);
    
    //회원 조회
    User findOne(Long userId);
    
    // 회원탈퇴
    
    //로그인
    String login(User user);

    // 로그아웃
    
    //비밀번호 변경
    
    // 비밀번호 찾기
}
