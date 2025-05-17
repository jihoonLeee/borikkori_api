package borikkori.community.api.adapter.out.redis;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;
import borikkori.community.api.adapter.out.redis.repository.RefreshTokenRepository;
import borikkori.community.api.application.port.RefreshTokenServicePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceAdapter implements RefreshTokenServicePort {
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	@Transactional
	public void saveTokenInfo(Long userId, String refreshToken, String accessToken) {
		refreshTokenRepository.save(new RefreshTokenEntity(refreshToken, userId, accessToken));
	}

	@Override
	@Transactional
	public void removeRefreshToken(String refreshToken) {
		refreshTokenRepository.deleteById(refreshToken);
	}

	@Override
	public Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken) {
		return refreshTokenRepository.findById(refreshToken);
	}
}
