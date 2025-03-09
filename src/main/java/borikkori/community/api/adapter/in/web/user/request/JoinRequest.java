package borikkori.community.api.adapter.in.web.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import borikkori.community.api.common.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    private String name;
    private String email;
    private String password;
    private final Role role = Role.USER;
    private int verificationNumber;

}
