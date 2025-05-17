package borikkori.community.api.adapter.out.redis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@RedisHash(value = "refreshToken", timeToLive = 6000) // 테스트 후 길게 바꾸기 ex 1주일
public class RefreshTokenEntity {

	@Id
	private String refreshToken;
	private Long userId;
	private String accessToken;
}
