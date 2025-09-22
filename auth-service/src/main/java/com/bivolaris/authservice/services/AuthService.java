package com.bivolaris.authservice.services;


import com.bivolaris.authservice.dtos.*;
import com.bivolaris.authservice.entities.User;
import com.bivolaris.authservice.repositories.UserRepository;
import com.bivolaris.authservice.security.Jwt;
import com.bivolaris.authservice.security.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public CreateAuthAccountResponse createAuthAccount(CreateAuthAccountRequest request) {
        var user = userRepository.findById(request.getUserId());
        if (user.isPresent()) {
            CreateAuthAccountResponse createAuthAccountResponse = new CreateAuthAccountResponse();
            createAuthAccountResponse.setMessage("Account already exists.");
            createAuthAccountResponse.setStatus(false);
            log.info("Account already exists.");
            return createAuthAccountResponse;
        }

        try {

            var encryptedPassword = passwordEncoder.encode(request.getPassword());

            User newUser = new User();
            newUser.setId(request.getUserId());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(encryptedPassword);
            userRepository.save(newUser);
            log.info("New Auth for User created.");
            CreateAuthAccountResponse createAuthAccountResponse = new CreateAuthAccountResponse();
            createAuthAccountResponse.setMessage("Account created.");
            createAuthAccountResponse.setStatus(true);
            return createAuthAccountResponse;


        } catch (Exception e) {
            log.error("Error in saving user");
        }
        CreateAuthAccountResponse createAuthAccountResponse = new CreateAuthAccountResponse();
        createAuthAccountResponse.setMessage("Something went wrong.");
        createAuthAccountResponse.setStatus(false);
        return createAuthAccountResponse;

    }

    public Boolean changePassword(ChangePasswordRequest request, UUID userId) {
        if (!request.getOldPassword().equals(request.getNewPassword())) {
            log.info("Old passwords do not match");
            return false;
        }
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.info("User not found");
            return false;
        }
        try {
            user.setPassword(request.getNewPassword());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error in saving user");
        }

        return false;

    }


    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);

    }

    public Jwt refreshAccessToken(String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        UUID userId = UUID.fromString(jwt.getUserId());
        var user = userRepository.findById(userId).orElseThrow();
        return jwtService.generateAccessToken(user);
    }



}
