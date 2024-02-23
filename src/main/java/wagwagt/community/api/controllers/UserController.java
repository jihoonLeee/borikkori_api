package wagwagt.community.api.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.infrastructures.security.JwtTokenProvider;
import wagwagt.community.api.requests.JoinRequest;
import wagwagt.community.api.requests.LoginRequest;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.responses.LoginResponse;
import wagwagt.community.api.usecases.EmailVerificationUsecase;
import wagwagt.community.api.usecases.UserUsecase;

import java.net.URI;

@Tag(name="user_api", description = "USER Apis")
@RequestMapping("users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final EmailVerificationUsecase emailVerificationUsecase;
    private final UserUsecase userUsecase;

    @Operation(summary = "이메일 전송" , description = "이메일 전송")
    @Parameter(name = "JoinRequest",description = "2번 반복할 문자열")
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody JoinRequest joinDto){
        emailVerificationUsecase.sendEmail(joinDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Operation(summary = "회원가입" , description = "회원가입 요청")
    @Parameter(name = "JoinRequest")
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest dto){
        if(emailVerificationUsecase.checkEmail(dto.getVerificationNumber(),dto.getEmail())){
            User user = User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .build();
            Authority auth = Authority.builder()
                    .user(user)
                    .role(dto.getRole())
                    .build();
            user.setAuth(auth);
            userUsecase.join(user);
            return ResponseEntity.created(URI.create("/join/"+user.getId())).build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "로그인" , description = "로그인 요청")
    @Parameter(name = "LoginRequest")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response){
        LoginResponse res = userUsecase.login(request,response);
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
            LoginResponse response = userUsecase.loginInfo(token);
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<String> handleLogout() {
        return ResponseEntity.ok().body("Logout successful");
    }
}
