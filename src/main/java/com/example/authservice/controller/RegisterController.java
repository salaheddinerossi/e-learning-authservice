package com.example.authservice.controller;


import com.example.authservice.dto.UserDto;
import com.example.authservice.exception.UnauthorizedException;
import com.example.authservice.response.UserCreatedResponse;
import com.example.authservice.service.UserService;
import com.example.authservice.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/register")
public class RegisterController {

    final
    UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<UserCreatedResponse>> registerAdmin(@RequestBody UserDto userDto,@AuthenticationPrincipal UserDetails userDetails){

        String role = userDetails.getAuthorities().isEmpty() ? null :
                userDetails.getAuthorities().iterator().next().getAuthority();

        if (!Objects.equals(role, "ROLE_ADMIN")){
            throw new UnauthorizedException("you have to be an admin to perform this action");
        }

        UserCreatedResponse userCreatedResponse = userService.RegisterAdmin(userDto);
        return ResponseEntity.ok(new ApiResponse<>(true,"admin account created",userCreatedResponse));

    }

    @PostMapping("/student")
    public ResponseEntity<ApiResponse<UserCreatedResponse>> registerStudent(@RequestBody UserDto userDto){

        UserCreatedResponse userCreatedResponse =userService.RegisterStudent(userDto);
        return ResponseEntity.ok(new ApiResponse<>(true,"student account created",userCreatedResponse));

    }

    @PostMapping("/teacher")
    public ResponseEntity<ApiResponse<UserCreatedResponse>> registerTeacher(@RequestBody UserDto userDto){

        UserCreatedResponse userCreatedResponse =userService.RegisterTeacher(userDto);
        return ResponseEntity.ok(new ApiResponse<>(true,"teacher account created",userCreatedResponse));

    }


}
