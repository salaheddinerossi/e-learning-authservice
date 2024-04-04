package com.example.authservice.controller;

import com.example.authservice.dto.PasswordDto;
import com.example.authservice.service.UserService;
import com.example.authservice.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

    private final UserService userService;

    @Autowired
    public PasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody PasswordDto passwordDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Token is not valid", null));
        }
        if (userService.changePassword(passwordDto, userDetails.getUsername())) {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, "Password has been changed", null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Passwords are not matching", null));
        }
    }
}
