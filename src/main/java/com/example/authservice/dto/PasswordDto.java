package com.example.authservice.dto;

import lombok.Data;

@Data
public class PasswordDto {

    private String oldPassword;

    private String newPassword;

}
