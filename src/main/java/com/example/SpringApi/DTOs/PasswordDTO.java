package com.example.SpringApi.DTOs;

public class PasswordDTO {

    private String password;

    public PasswordDTO() {} // Default constructor

    public PasswordDTO(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
