package com.bivolaris.authservice.services;


import java.util.Optional;

import com.bivolaris.authservice.entities.User;
import com.bivolaris.authservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
