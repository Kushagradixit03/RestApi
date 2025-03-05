package com.example.SpringApi.Controllers;

import com.example.SpringApi.DTOs.MailDTO;
import com.example.SpringApi.Models.AuthUser;
import com.example.SpringApi.Services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.SpringApi.Services.EmailService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    EmailService emailService;
    private final AuthUserService authUserService;

    @Autowired
    public AuthUserController(AuthUserService authUserService,EmailService emailService) {
        this.authUserService = authUserService;
        this.emailService = emailService;
    }
//
//    @PostMapping("/register")
//    public String registerUser(@RequestBody @Valid AuthUser authUser) {
//        Optional<AuthUser> existingUser = authUserService.findByEmail(authUser.getEmail());
//        if (existingUser.isPresent()) {
//            return "Email is already in use";
//        }
//        authUserService.save(authUser);  // Save the new user
//        return "User registered successfully!";
//    }
    @PostMapping("/register") // Ensures the endpoint only accepts POST requests
    public String registerUser(@RequestBody @Valid AuthUser authUser) {
        Optional<AuthUser> existingUser = authUserService.findByEmail(authUser.getEmail());
        if (existingUser.isPresent()) {
            return "Email is already in use";
        }
        authUserService.save(authUser);
        return "User registered successfully!";
}

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        String token = authUserService.authenticateUser(
                request.get("email"),
                request.get("password")
        );

        if (token.equals("User not found!") || token.equals("Invalid email or password!")) {
            return ResponseEntity.status(401).body(Map.of("error", token));
        }

        return ResponseEntity.ok(Map.of("message", "Login successful!", "token", token));
    }
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody MailDTO message){
        emailService.sendEmail(message.getTo(), message.getSubject(),message.getBody());
        return "Mail sent";
    }

}
