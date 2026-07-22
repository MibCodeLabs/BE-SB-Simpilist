package com.mib.simpilist.store;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

import static com.mib.simpilist.utililty.Constants.REFRESH_TOKEN_KEY_PREFIX;
import static com.mib.simpilist.utililty.Utilities.sha256;

@Component
public class RefreshTokenStore {

    private final RedisTemplate<String, String> redisTemplate;

    public RefreshTokenStore(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(String tokenId) {
        return REFRESH_TOKEN_KEY_PREFIX + sha256(tokenId);
    }

    public void save(String tokenId, String userId, Duration ttl) {
        redisTemplate.opsForValue().set(
                key(tokenId),
                userId,
                ttl
        );
    }

    public Optional<String> get(String tokenId) {
        String value = redisTemplate.opsForValue().get(key(tokenId));
        return Optional.ofNullable(value);
    }

    public boolean exists(String tokenId) {
        return redisTemplate.hasKey(key(tokenId));
    }

    public void delete(String tokenId) {
        redisTemplate.delete(key(tokenId));
    }
}