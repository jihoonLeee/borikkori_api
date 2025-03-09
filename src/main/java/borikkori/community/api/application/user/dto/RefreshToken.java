package borikkori.community.api.application.user.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value = "refreshToken" , timeToLive = 6000) // 테스트 후 길게 바꾸기 ex 1주일
public class RefreshToken {

    @Id
    private Long id;

    private String refreshToken;

    @Indexed
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
