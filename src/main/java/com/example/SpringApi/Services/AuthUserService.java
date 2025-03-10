package com.example.SpringApi.Services;

import com.example.SpringApi.DTOs.AuthUserDTO;
import com.example.SpringApi.DTOs.LoginDTO;
import com.example.SpringApi.DTOs.PasswordDTO;
import com.example.SpringApi.DTOs.ResetPasswordDTO;
import com.example.SpringApi.Interfaces.IAuthUserInterface;
import com.example.SpringApi.Models.AuthUser;
import com.example.SpringApi.Repositories.AuthUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthUserService implements IAuthUserInterface {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserService.class);

    private final AuthUserRepository userRepository;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder bcrypt;

    @Autowired
    public AuthUserService(AuthUserRepository userRepository, EmailService emailService,
                           JwtTokenService jwtTokenService, BCryptPasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtTokenService = jwtTokenService;
        this.bcrypt = bcrypt;
    }

    @Override
    public String register(AuthUserDTO user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "{\"error\": \"User already registered\"}";
        }

        String hashPass = bcrypt.encode(user.getPassword());

        AuthUser newUser = new AuthUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), hashPass);
        userRepository.save(newUser);

        emailService.sendEmail(user.getEmail(), "Your Account is Ready!",
                "UserName: " + user.getFirstName() + " " + user.getLastName() +
                        "\nEmail: " + user.getEmail() +
                        "\nYou are registered!\nBest Regards,\nBridgelabz Team");

        return "{\"message\": \"User registered successfully\"}";
    }

    @Override
    public String login(LoginDTO user) {
        AuthUser foundUser = userRepository.findByEmail(user.getEmail());

        if (foundUser == null) {
            return "{\"error\": \"User not registered\"}";
        }

        if (!bcrypt.matches(user.getPassword(), foundUser.getHashPass())) {
            return "{\"error\": \"Invalid password\"}";
        }

        String token = jwtTokenService.createToken(foundUser.getId());

        foundUser.setToken(token);
        userRepository.save(foundUser);

        return "{\"message\": \"User logged in\", \"token\": \"" + token + "\"}";
    }

    @Override
    @Transactional
    public String forgotPassword(PasswordDTO pass, String email) {
        logger.info("Forgot Password Request for: {}", email);

        if (pass == null || pass.getPassword() == null || pass.getPassword().trim().isEmpty()) {
            return "{\"error\": \"Password cannot be null or empty!\"}";
        }

        AuthUser foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            return "{\"error\": \"User not registered!\"}";
        }

        logger.info("User Found: {}", foundUser.getEmail());

        String hashPass = bcrypt.encode(pass.getPassword());
        foundUser.setHashPass(hashPass);
        userRepository.save(foundUser);

        emailService.sendEmail(email, "Password Reset Status", "Your password has been reset");

        logger.info("Password Reset Successfully!");

        return "{\"message\": \"Password reset successfully!\"}";
    }

    @Override
    @Transactional
    public String resetPassword(String email, ResetPasswordDTO resetPasswordDTO) {
        logger.info("Reset Password Request for: {}", email);

        AuthUser foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            return "{\"error\": \"User not found with email: " + email + "\"}";
        }

        if (!bcrypt.matches(resetPasswordDTO.getCurrentPassword(), foundUser.getHashPass())) {
            return "{\"error\": \"Current password is incorrect!\"}";
        }

        String hashNewPass = bcrypt.encode(resetPasswordDTO.getNewPassword());
        foundUser.setHashPass(hashNewPass);
        userRepository.save(foundUser);

        logger.info("Password Reset Successfully for: {}", email);
        return "{\"message\": \"Password reset successfully!\"}";
    }
}
