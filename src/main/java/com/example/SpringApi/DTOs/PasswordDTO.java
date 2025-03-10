package com.example.SpringApi.DTOs;

import jakarta.validation.constraints.NotBlank;

public class PasswordDTO {

    @NotBlank(message = "Password cannot be null or empty!")
    private String password;

    public PasswordDTO() {} // Default constructor

    public PasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
