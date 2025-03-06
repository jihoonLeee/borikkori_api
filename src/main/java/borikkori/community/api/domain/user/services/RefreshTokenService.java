package borikkori.community.api.domain.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.domain.user.interfaces.dto.RefreshToken;
import borikkori.community.api.domain.user.interfaces.repositories.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveTokenInfo(Long userId, String refreshToken,String accessToken){
        refreshTokenRepository.save(new RefreshToken(userId,refreshToken,accessToken));
    }

    @Transactional
    public void removeRefreshToken(String accessToken){
        refreshTokenRepository.findByAccessToken(accessToken)
                .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
    }
}
