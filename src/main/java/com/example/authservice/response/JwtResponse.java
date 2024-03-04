package com.example.authservice.response;

import lombok.Data;

@Data
public class JwtResponse {
    private final String token;

    private String email;


    private String role;

}
