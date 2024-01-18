package wagwagt.community.api.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import wagwagt.community.api.enums.Role;

@Builder
@Getter
public class LoginResponse {
    @Schema(description = "유저 닉네임")
    String nickName;

    @Schema(description = "유저 유형",example = "DEFAULT")
    Role role;

    @Schema(description = "유저 이메일",example = "jihoonn@gmail.com")
    String email;

}
