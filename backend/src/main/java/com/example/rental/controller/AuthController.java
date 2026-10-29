package com.example.rental.controller;

import com.example.rental.domain.User;
import com.example.rental.dto.LoginRequest;
import com.example.rental.dto.RegisterRequest;
import com.example.rental.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request.getUsername(), request.getPassword());
        return Map.of("id", user.getId(), "username", user.getUsername());
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getUsername(), request.getPassword());
        return Map.of("id", user.getId(), "username", user.getUsername());
    }
}
