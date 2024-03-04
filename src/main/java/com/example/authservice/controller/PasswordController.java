package com.example.authservice.controller;


import com.example.authservice.dto.PasswordDto;
import com.example.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

    final
    UserService userService;

    public PasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto,@AuthenticationPrincipal UserDetails userDetails){

        if(userDetails==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token is not valid");
        }
        if(userService.changePassword(passwordDto,userDetails.getUsername())){
            return ResponseEntity.ok("password has been changed");

        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("passwords are not matching");

        }

    }
}
