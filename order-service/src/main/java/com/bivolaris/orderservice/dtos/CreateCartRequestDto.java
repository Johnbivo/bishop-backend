package com.bivolaris.orderservice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCartRequestDto {

    private UUID userId;
    private String sessionId;
}
