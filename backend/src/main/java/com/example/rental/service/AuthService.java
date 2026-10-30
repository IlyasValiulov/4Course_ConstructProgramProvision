package com.example.rental.service;

import com.example.rental.domain.User;
import com.example.rental.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Прототип: пароль хранится в открытом виде (passwordHash = password).
     */
    public User register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username already taken");
        }
        User user = new User(username, password, User.Role.USER);
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.getPasswordHash().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }
}
