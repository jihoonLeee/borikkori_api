package borikkori.community.api.application.port;

public interface RedisServicePort {
    void setValue(String key, String value, long durationInMinutes);
    String getValue(String key);
    void deleteValue(String key);
}
