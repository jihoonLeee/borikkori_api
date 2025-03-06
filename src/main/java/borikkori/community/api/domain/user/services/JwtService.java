package borikkori.community.api.domain.user.services;

import io.jsonwebtoken.Claims;
import borikkori.community.api.domain.user.entities.Authority;

import java.util.List;

public interface JwtService {
    String createToken(String email, String name, List<Authority> roles);
    String createRefreshToken(String email);
    Claims getInfo(String token);
}
