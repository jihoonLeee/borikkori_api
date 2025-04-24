package borikkori.community.api.adapter.in.web.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	private String email;
	private String password;

}
