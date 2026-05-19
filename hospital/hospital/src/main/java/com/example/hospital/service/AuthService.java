package com.example.hospital.service;

import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.response.AuthResponse;
import com.example.hospital.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.hospital.service.JwtService;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; // your JWT utility class

    public AuthResponse login(String email, String password,String role) {

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Password not set");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.getRole().equalsIgnoreCase(role)) {
            throw new RuntimeException("Role mismatch");
        }


        String token = jwtService.generateToken(user);

             UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getName()
        );
        System.out.println("LOGIN USER ID = " + user.getId());
        System.out.println("RESPONSE ID = " + userResponse.getId());

        // ✅ RETURN THIS
        return new AuthResponse(token, userResponse);    }
}