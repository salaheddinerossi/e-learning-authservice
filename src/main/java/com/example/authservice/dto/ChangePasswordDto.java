package com.example.authservice.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {

    private Long id;

    private String currentPassword;

    private String newPassword;
}
