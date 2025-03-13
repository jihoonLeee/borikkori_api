package borikkori.community.api.adapter.out.redis.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value = "refreshToken" , timeToLive = 6000) // 테스트 후 길게 바꾸기 ex 1주일
public class RefreshTokenEntity {

    @Id
    private Long id;

    private String refreshToken;

    @Setter
    @Indexed
    private String accessToken;

}
