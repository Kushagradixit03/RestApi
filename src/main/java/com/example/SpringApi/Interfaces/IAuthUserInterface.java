package com.example.SpringApi.Interfaces;


import com.example.SpringApi.DTOs.AuthUserDTO;
import com.example.SpringApi.DTOs.LoginDTO;
import com.example.SpringApi.DTOs.PasswordDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAuthUserInterface {

    public String register(AuthUserDTO user);


    public String login(LoginDTO user);

    public AuthUserDTO forgotPassword(PasswordDTO pass, String email);



}