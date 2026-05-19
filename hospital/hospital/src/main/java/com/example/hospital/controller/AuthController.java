package com.example.hospital.controller;

import com.example.hospital.model.User;
import com.example.hospital.response.AuthResponse;
import com.example.hospital.service.AuthService;
import com.example.hospital.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.hospital.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String password = data.get("password");

        if (email == null || password == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email and password are required"));
        }

        try {
            AuthResponse response = service.login(email, password);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // Check email already exists
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedUser(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7); // remove "Bearer "
        String email = jwtService.extractEmail(token);

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("TOKEN: " + token);

        return ResponseEntity.ok(
                Map.of(        "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole()
                )
        );
    }
}