package com.example.SpringApi.Interfaces;

import com.example.SpringApi.DTOs.AuthUserDTO;
import com.example.SpringApi.DTOs.LoginDTO;
import com.example.SpringApi.DTOs.PasswordDTO;
import com.example.SpringApi.DTOs.ResetPasswordDTO;

public interface IAuthUserInterface {

    String register(AuthUserDTO user);

    String login(LoginDTO user);

    String forgotPassword(PasswordDTO pass, String email);

    String resetPassword(String email, ResetPasswordDTO resetPasswordDTO);
}
