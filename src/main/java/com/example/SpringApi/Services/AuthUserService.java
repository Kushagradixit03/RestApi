package com.example.SpringApi.Services;

import com.example.SpringApi.Models.AuthUser;
import com.example.SpringApi.Repositories.AuthUserRepository;
import com.example.SpringApi.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired(required=true)
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register User
    public String registerUser(AuthUser authUser) {
        if (authUserRepository.existsByEmail(authUser.getEmail())) {
            return "Email is already in use!";
        }

        // Encrypt password before saving
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUserRepository.save(authUser);

        return "User registered successfully!";
    }

    // Authenticate User and Generate Token
    public  String authenticateUser(String email, String password) {
        Optional<AuthUser> userOpt = authUserRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        AuthUser user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid email or password!";
        }

        // Generate JWT Token using HMAC256
        return jwtUtil.generateToken(email);
    }
    // Find User by Email
    public Optional<AuthUser> findByEmail(String email) {
        return authUserRepository.findByEmail(email);
    }

    // Save New User Data
    public AuthUser save(AuthUser authUser) {
        // Encrypt password before saving the user data
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        return authUserRepository.save(authUser); // Save user to the database
    }
}
