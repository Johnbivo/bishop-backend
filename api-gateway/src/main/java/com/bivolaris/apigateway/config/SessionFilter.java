package com.bivolaris.apigateway.config;

import com.bivolaris.apigateway.entities.SessionData;
import com.bivolaris.apigateway.services.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class SessionFilter implements GlobalFilter, Ordered {

    private final RedisService redisService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String sessionId = request.getHeaders().getFirst("session-id");

        log.debug("Processing request with session-id: {}", sessionId);

        if (sessionId == null || !isValidSession(sessionId)) {
            sessionId = redisService.createGuestSession();
            log.debug("Created new guest session: {}", sessionId);
        } else {
            // Update last accessed time for existing valid sessions
            updateSessionAccess(sessionId);
        }

        ServerHttpRequest mutatedRequest = request.mutate()
                .header("session-id", sessionId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isValidSession(String sessionId) {
        try {
            SessionData sessionData = (SessionData) redisTemplate.opsForValue().get(sessionId);
            return sessionData != null;
        } catch (Exception e) {
            log.warn("Error validating session {}: {}", sessionId, e.getMessage());
            return false;
        }
    }

    private void updateSessionAccess(String sessionId) {
        try {
            SessionData sessionData = (SessionData) redisTemplate.opsForValue().get(sessionId);
            if (sessionData != null) {
                sessionData.setLastAccessedAt(java.time.Instant.now());
                Duration ttl = "USER".equals(sessionData.getType()) ?
                        java.time.Duration.ofHours(24) : java.time.Duration.ofHours(4);
                redisTemplate.opsForValue().set(sessionId, sessionData, ttl);
            }
        } catch (Exception e) {
            log.warn("Error updating session access time for {}: {}", sessionId, e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}