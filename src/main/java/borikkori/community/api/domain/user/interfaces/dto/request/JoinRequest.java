package borikkori.community.api.domain.user.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import borikkori.community.api.domain.user.entities.enums.Role;

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
