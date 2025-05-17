package borikkori.community.api.application.port;

import java.util.Optional;

import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;

public interface RefreshTokenServicePort {
	void saveTokenInfo(Long userId, String refreshToken, String accessToken);

	void removeRefreshToken(String refreshToken);

	Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
