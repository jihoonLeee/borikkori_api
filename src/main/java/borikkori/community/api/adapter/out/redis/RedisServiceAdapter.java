package borikkori.community.api.adapter.out.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import borikkori.community.api.application.port.RedisServicePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceAdapter implements RedisServicePort {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void setValue(String key, String value, long durationInMinutes) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set(key, value, Duration.ofMinutes(durationInMinutes));
	}

	@Override
	public String getValue(String key) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		return values.get(key);
	}

	@Override
	public void deleteValue(String key) {
		redisTemplate.delete(key);
	}
}
