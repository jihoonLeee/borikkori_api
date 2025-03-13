package borikkori.community.api.application.port;
import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;
import io.jsonwebtoken.Claims;
import java.util.List;

public interface JwtServicePort {
    String createToken(String email, String name, List<Role> roles);
    String createRefreshToken(String email);
    Claims getInfo(String token);
}