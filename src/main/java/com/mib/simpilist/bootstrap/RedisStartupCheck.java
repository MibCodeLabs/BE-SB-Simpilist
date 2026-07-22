package com.mib.simpilist.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "RedisStartupCheck")
public class RedisStartupCheck {

    private final RedisConnectionFactory connectionFactory;

    public RedisStartupCheck(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void checkRedis() {
        try (var connection = connectionFactory.getConnection()) {
            String response = connection.ping();

            if (!"PONG".equalsIgnoreCase(response)) {
                log.error("Invalid response by Redis during startup");
                throw new IllegalStateException(
                        "Unexpected Redis response: " + response
                );
            }
            log.info("Successfully connected to Redis");
        } catch (Exception e) {
            log.error("Failed to connect to Redis during startup", e);
            throw new IllegalStateException("Redis is NOT available. Application startup aborted.", e);
        }
    }
}