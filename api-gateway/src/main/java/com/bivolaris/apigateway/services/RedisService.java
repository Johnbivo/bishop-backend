package com.bivolaris.apigateway.services;

import com.bivolaris.apigateway.entities.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public String createGuestSession() {
        String sessionId = UUID.randomUUID().toString();
        SessionData sessionData = new SessionData();
        sessionData.setType("GUEST");

        redisTemplate.opsForValue().set(sessionId, sessionData, Duration.ofHours(4));
        return sessionId;
    }

    public String createUserSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        SessionData sessionData = new SessionData();
        sessionData.setType("USER");
        sessionData.setUserId(userId);

        redisTemplate.opsForValue().set(sessionId, sessionData, Duration.ofHours(24));
        return sessionId;
    }

    public void upgradeGuestToUser(String sessionId, String userId) {
        SessionData sessionData = (SessionData) redisTemplate.opsForValue().get(sessionId);
        if (sessionData != null) {
            sessionData.setType("USER");
            sessionData.setUserId(userId);
            sessionData.setLastAccessedAt(Instant.now());
            redisTemplate.opsForValue().set(sessionId, sessionData, Duration.ofHours(24));
        }
    }

}
