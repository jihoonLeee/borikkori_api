package borikkori.community.api.application.port;

import java.util.List;

import borikkori.community.api.common.enums.Role;
import io.jsonwebtoken.Claims;

public interface JwtServicePort {
	String createToken(String email, String name, List<Role> roles);

	String createRefreshToken(String email);

	Claims getInfo(String token);
}
