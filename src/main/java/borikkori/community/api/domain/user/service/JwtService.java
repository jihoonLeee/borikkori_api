package borikkori.community.api.domain.user.service;

import io.jsonwebtoken.Claims;
import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;

import java.util.List;

public interface JwtService {
    String createToken(String email, String name, List<RoleEntity> roles);
    String createRefreshToken(String email);
    Claims getInfo(String token);
}
