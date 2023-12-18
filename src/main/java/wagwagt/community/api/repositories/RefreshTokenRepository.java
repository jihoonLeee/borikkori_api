package wagwagt.community.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.dto.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
