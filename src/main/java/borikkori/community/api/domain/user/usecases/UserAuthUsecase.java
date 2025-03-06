package borikkori.community.api.domain.user.usecases;

import jakarta.servlet.http.HttpServletResponse;
import borikkori.community.api.common.exeptions.NotExistAuthException;
import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.domain.user.interfaces.dto.request.JoinRequest;
import borikkori.community.api.domain.user.interfaces.dto.request.LoginRequest;
import borikkori.community.api.domain.user.interfaces.dto.response.LoginResponse;

import java.util.Optional;

public interface UserAuthUsecase {


    //로그인
    LoginResponse login(LoginRequest user, HttpServletResponse response);

    //로그인 정보
     LoginResponse loginInfo (String token) throws Exception;

    //비밀번호 변경
    
    // 비밀번호 찾기
}
