package borikkori.community.api.adapter.in.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.adapter.in.web.user.request.LoginRequest;
import borikkori.community.api.adapter.in.web.user.response.LoginResponse;
import borikkori.community.api.application.user.usecase.EmailVerificationUsecase;
import borikkori.community.api.application.user.usecase.UserAuthUsecase;
import borikkori.community.api.application.user.usecase.UserManagementUsecase;

import java.net.URI;

@Tag(name="user_api", description = "USER Apis")
@RequestMapping("user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final EmailVerificationUsecase emailVerificationUsecase;
    private final UserAuthUsecase userAuthUsecase;
    private final UserManagementUsecase userManagementUsecase;

    @Operation(summary = "이메일 전송" , description = "이메일 전송")
    @Parameter(name = "JoinRequest",description = "2번 반복할 문자열")
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody JoinRequest req){
        emailVerificationUsecase.sendEmail(req.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Operation(summary = "회원가입" , description = "회원가입 요청")
    @Parameter(name = "JoinRequest")
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest req){
        if(emailVerificationUsecase.checkEmail(req.getVerificationNumber(),req.getEmail())){
            return ResponseEntity.created(URI.create("/join/"+ userManagementUsecase.join(req))).build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "로그인" , description = "로그인 요청")
    @Parameter(name = "LoginRequest")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletResponse response){
        LoginResponse res = userAuthUsecase.login(req,response);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


    @Operation(summary = "로그인 상태 체크" , description = "로그인 상태 체크")
    @PostMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(401).body("로그인하지 않았습니다.");
        } else {
            LoginResponse response = userAuthUsecase.loginInfo(token);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> handleLogout() {
        return ResponseEntity.ok().body("Logout successful");
    }
}
