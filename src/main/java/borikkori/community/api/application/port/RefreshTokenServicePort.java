package borikkori.community.api.application.port;

public interface RefreshTokenServicePort {
    void saveTokenInfo(Long userId, String refreshToken, String accessToken);
    void removeRefreshToken(String accessToken);
}
