package borikkori.community.api.adapter.out.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity,String> {
    Optional<RefreshTokenEntity> findByAccessToken(String accessToken);
}
