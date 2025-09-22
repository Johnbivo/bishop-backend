package com.bivolaris.apigateway.entities;

import lombok.Data;
import java.io.Serializable;
import java.time.Instant;


@Data
public class SessionData implements Serializable {
    private String type; // "GUEST" or "USER"
    private String userId;
    private Instant createdAt;
    private Instant lastAccessedAt;

    public SessionData() {
        this.createdAt = Instant.now();
        this.lastAccessedAt = Instant.now();
    }


}
