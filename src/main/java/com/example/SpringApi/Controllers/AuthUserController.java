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
//<======================== UC9 --> For Registration and Login of a user================================>
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthUserDTO user) {
        try {
            String response = iAuthUserInterface.register(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO user) {
        String token = iAuthUserInterface.login(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
//<=======================UC10-Sending mails===============================>
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@Valid @RequestBody MailDTO message) {
        emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
        return ResponseEntity.ok("Mail sent successfully.");
    }
//<==================  //UC11- Added swagger ===============================>
//<================== //UC12-Forgot Passward================================>
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<AuthUserDTO> forgotPassword(@RequestBody PasswordDTO pass, @PathVariable String email) {
        return ResponseEntity.ok(iAuthUserInterface.forgotPassword(pass, email));
    }
}
