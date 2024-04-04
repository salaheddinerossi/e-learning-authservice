package com.example.authservice.service;

import com.example.authservice.dto.PasswordDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.model.User;
import com.example.authservice.response.UserCreatedResponse;

import java.util.Optional;

public interface UserService {

    UserCreatedResponse RegisterStudent(UserDto userDto);

    UserCreatedResponse RegisterAdmin(UserDto userDto);

    UserCreatedResponse RegisterTeacher(UserDto userDto);

    Optional<User> findUserByEmail(String email);

    Boolean changePassword(PasswordDto passwordDto, String email);


}
