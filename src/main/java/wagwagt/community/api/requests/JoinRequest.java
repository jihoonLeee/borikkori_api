package wagwagt.community.api.requests;

import lombok.Getter;
import lombok.Setter;
import wagwagt.community.api.enums.Role;

@Getter
@Setter
public class JoinRequest {

    private String name;
    private String email;
    private String password;
    private final Role role = Role.NORMAL;
    private int verificationNumber;
}
