package com.example.authservice.response;


import lombok.Data;

@Data
public class UserCreatedResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;


    private String phoneNumber;
}
