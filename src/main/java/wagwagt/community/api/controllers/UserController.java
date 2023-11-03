package wagwagt.community.api.controllers;

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

    @PostMapping("/sendEmail")
    public String sendEmail(JoinDto dto){
        emailVerificationUsecase.sendEmail(dto.getEmail());

        return "";
    }

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
