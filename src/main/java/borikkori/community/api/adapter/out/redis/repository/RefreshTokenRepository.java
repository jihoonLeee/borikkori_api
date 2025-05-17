package borikkori.community.api.adapter.out.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
}
