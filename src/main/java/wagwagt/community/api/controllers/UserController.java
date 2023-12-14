package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.requests.JoinRequest;
import wagwagt.community.api.requests.LoginRequest;
import wagwagt.community.api.entities.User;
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
    @Parameter(name = "joinDto",description = "2번 반복할 문자열")
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody JoinRequest joinDto){
        emailVerificationUsecase.sendEmail(joinDto.getEmail());

        return "";
    }
    
    @Operation(summary = "회원가입" , description = "회원가입 요청")
    @Parameter(name = "JoinDto")
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
    @Parameter(name = "LoginDto")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        System.out.println(request.getEmail() + "  : " + request.getPassword());
        return userUsecase.login(request).toString();
    }

}
