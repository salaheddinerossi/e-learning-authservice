package com.example.authservice.controller;

import com.example.authservice.response.UserDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailsController {

    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            String role = userDetails.getAuthorities().isEmpty() ? null :
                    userDetails.getAuthorities().iterator().next().getAuthority();

            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            userDetailsResponse.setEmail(userDetails.getUsername());
            userDetailsResponse.setRole(role);
            return ResponseEntity.ok(userDetailsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

}
