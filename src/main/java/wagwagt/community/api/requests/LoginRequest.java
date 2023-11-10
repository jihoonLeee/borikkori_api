package wagwagt.community.api.requests;

import lombok.Getter;
import lombok.Setter;
import wagwagt.community.api.enums.Role;

import java.util.List;

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;

}
