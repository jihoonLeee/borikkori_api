package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface UserAuthenticationUsecase {

	//로그인
	UserResponse login(LoginRequest user, HttpServletResponse response);

	//로그인 정보
	UserResponse loginInfo(String token) throws Exception;

	//비밀번호 변경

	// 비밀번호 찾기
}
