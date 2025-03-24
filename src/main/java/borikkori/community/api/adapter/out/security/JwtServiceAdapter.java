package borikkori.community.api.adapter.out.security;

import java.util.List;

import org.springframework.stereotype.Service;

import borikkori.community.api.application.port.JwtServicePort;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.config.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceAdapter implements JwtServicePort {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public String createToken(String email, String name, List<Role> roles) {
		return jwtTokenProvider.createToken(email, name, roles);
	}

	@Override
	public String createRefreshToken(String email) {
		return jwtTokenProvider.createRefreshToken(email);
	}

	@Override
	public Claims getInfo(String token) {
		return jwtTokenProvider.getInfo(token);
	}

}
