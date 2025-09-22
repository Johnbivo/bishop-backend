package com.bivolaris.authservice.controllers;


import com.bivolaris.authservice.dtos.JwtResponse;
import com.bivolaris.authservice.dtos.LoginRequest;
import com.bivolaris.authservice.dtos.LoginResponse;
import com.bivolaris.authservice.mappers.UserMapper;
import com.bivolaris.authservice.security.JwtConfig;
import com.bivolaris.authservice.security.JwtService;
import com.bivolaris.authservice.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public JwtResponse login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        var loginResult = authService.login(request);

        var refreshToken = loginResult.getRefreshToken().toString();
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(loginResult.getAccessToken().toString());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        var accessToken = authService.refreshAccessToken(refreshToken);
        return new JwtResponse(accessToken.toString());
    }


    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        // Authorization: Bearer <token>
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return jwtService.validateToken(authHeader.replace("Bearer ", ""))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
