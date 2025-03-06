package borikkori.community.api.domain.user.services.implementations;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import borikkori.community.api.domain.user.entities.Authority;
import borikkori.community.api.domain.user.services.JwtService;
import borikkori.community.api.infrastructures.provider.JwtTokenProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String createToken(String email, String name, List<Authority> roles) {
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
