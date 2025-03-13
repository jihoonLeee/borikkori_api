package borikkori.community.api.adapter.out.redis;

import borikkori.community.api.application.port.RefreshTokenServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.redis.entity.RefreshTokenEntity;
import borikkori.community.api.adapter.out.redis.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceAdapter implements RefreshTokenServicePort {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void saveTokenInfo(Long userId, String refreshToken, String accessToken) {
        refreshTokenRepository.save(new RefreshTokenEntity(userId, refreshToken, accessToken));
    }

    @Override
    @Transactional
    public void removeRefreshToken(String accessToken) {
        refreshTokenRepository.findByAccessToken(accessToken)
                .ifPresent(token -> refreshTokenRepository.delete(token));
    }
}
