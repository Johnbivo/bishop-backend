package com.bivolaris.authservice.dtos;

import com.bivolaris.authservice.security.Jwt;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {

    private Jwt accessToken;
    private Jwt refreshToken;
}
