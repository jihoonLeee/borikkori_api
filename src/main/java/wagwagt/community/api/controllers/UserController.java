package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wagwagt.community.api.dtos.JoinDto;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.usecases.EmailVerificationUsecase;
import wagwagt.community.api.usecases.UserUsecase;

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
    public String sendEmail(JoinDto joinDto){
        emailVerificationUsecase.sendEmail(joinDto.getEmail());

        return "";
    }
    
    @Operation(summary = "회원가입" , description = "회원가입 요청")
    @Parameter(name = "joinDto",description = "2번 반복할 문자열")
    @PostMapping("/join")
    public String join(@RequestBody JoinDto dto){
        if(emailVerificationUsecase.checkEmail(dto.getVerificationNumber(),dto.getEmail())){
            User user = User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .build();
            userUsecase.join(user);
            return "OK";
        }else{
            return "error";
        }
    }

}
