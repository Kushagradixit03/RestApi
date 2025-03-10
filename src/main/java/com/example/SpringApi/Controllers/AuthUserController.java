package com.example.SpringApi.Controllers;

import com.example.SpringApi.DTOs.*;
import com.example.SpringApi.Services.EmailService;
import com.example.SpringApi.Interfaces.IAuthUserInterface;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthUserController {

    private final EmailService emailService;
    private final IAuthUserInterface iAuthUserInterface;

    public AuthUserController(EmailService emailService, IAuthUserInterface iAuthUserInterface) {
        this.emailService = emailService;
        this.iAuthUserInterface = iAuthUserInterface;
    }

    //<======================== UC9 --> User Registration ================================>
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody AuthUserDTO user) {
        Map<String, String> response = new HashMap<>();
        try {
            String message = iAuthUserInterface.register(user);
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //<======================== UC9 --> User Login ================================>
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO user) {
        Map<String, String> response = new HashMap<>();
        try {
            String token = iAuthUserInterface.login(user);
            response.put("message", "User logged in successfully");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    //<======================= UC10 - Sending Mails ===============================>
    @PostMapping("/sendMail")
    public ResponseEntity<Map<String, String>> sendMail(@Valid @RequestBody MailDTO message) {
        emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Mail sent successfully.");
        return ResponseEntity.ok(response);
    }
    //<==================  //UC11- Added swagger ===============================>

    //<================== UC12 - Forgot Password ===============================>
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody PasswordDTO pass, @PathVariable String email) {
        Map<String, String> response = new HashMap<>();
        try {
            iAuthUserInterface.forgotPassword(pass, email);
            response.put("message", "Password reset link sent to email.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //<================== UC13 - Reset Password ===============================>
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<Map<String, String>> resetPassword(
            @PathVariable String email,
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {

        Map<String, String> response = new HashMap<>();

        // Check if DTO fields are null or empty
        if (resetPasswordDTO.getCurrentPassword() == null || resetPasswordDTO.getCurrentPassword().isEmpty() ||
                resetPasswordDTO.getNewPassword() == null || resetPasswordDTO.getNewPassword().isEmpty()) {
            response.put("error", "Current and new password cannot be null or empty!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            String message = iAuthUserInterface.resetPassword(email, resetPasswordDTO);
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Something went wrong, please try again!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
