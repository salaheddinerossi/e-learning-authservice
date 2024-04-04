package com.example.authservice.controller;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.response.JwtResponse;
import com.example.authservice.util.ApiResponse;
import com.example.authservice.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/student")
    public ResponseEntity<ApiResponse<JwtResponse>> loginStudent(@RequestBody LoginDto loginDto) {
        return loginUser(loginDto, "ROLE_STUDENT");
    }

    @PostMapping("/teacher")
    public ResponseEntity<ApiResponse<JwtResponse>> loginTeacher(@RequestBody LoginDto loginDto) {
        return loginUser(loginDto, "ROLE_TEACHER");
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<JwtResponse>> loginAdmin(@RequestBody LoginDto loginDto) {
        return loginUser(loginDto, "ROLE_ADMIN");
    }

    private ResponseEntity<ApiResponse<JwtResponse>> loginUser(LoginDto loginDto, String expectedRole) {
        try {
            Authentication authentication = authenticate(loginDto.getEmail(), loginDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            boolean hasExpectedRole = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals(expectedRole));

            if (!hasExpectedRole) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(false, "User does not have the expected role: " + expectedRole, null));
            }

            String token = jwtTokenUtil.generateToken(userDetails.getUsername(), expectedRole);
            JwtResponse jwtResponse = new JwtResponse(token);
            jwtResponse.setEmail(userDetails.getUsername());
            jwtResponse.setRole(expectedRole);

            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", jwtResponse));
        } catch (Exception e) {
            String errorMessage = "An error occurred";
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (e instanceof BadCredentialsException) {
                errorMessage = "Invalid credentials";
                status = HttpStatus.UNAUTHORIZED;
            }
            return ResponseEntity.status(status).body(new ApiResponse<>(false, errorMessage, null));
        }
    }

    private Authentication authenticate(String email, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
