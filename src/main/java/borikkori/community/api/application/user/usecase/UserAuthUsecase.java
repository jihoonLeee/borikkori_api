package borikkori.community.api.application.user.usecase;

import jakarta.servlet.http.HttpServletResponse;
import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.adapter.in.web.user.response.LoginResponse;

public interface UserAuthUsecase {


    //로그인
    LoginResponse login(LoginRequest user, HttpServletResponse response);

    //로그인 정보
     LoginResponse loginInfo (String token) throws Exception;

    //비밀번호 변경
    
    // 비밀번호 찾기
}
