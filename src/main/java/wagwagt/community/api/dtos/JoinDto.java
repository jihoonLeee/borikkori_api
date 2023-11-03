package wagwagt.community.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {

    private String name;
    private String email;
    private String password;

    private int verificationNumber;
}
