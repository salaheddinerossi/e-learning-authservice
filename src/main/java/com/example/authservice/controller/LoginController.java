package com.example.authservice.controller;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.response.JwtResponse;
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
    public ResponseEntity<?> loginStudent(@RequestBody LoginDto loginDto) {
        return loginUser(loginDto, "ROLE_STUDENT");
    }

    @PostMapping("/teacher")
    public ResponseEntity<?> loginTeacher(@RequestBody LoginDto loginDto) {
        return loginUser(loginDto, "ROLE_TEACHER");
    }

    @PostMapping("/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginDto loginDto) {
        return loginUser(loginDto, "ROLE_ADMIN");
    }

    private ResponseEntity<?> loginUser(LoginDto loginDto, String expectedRole) {
        try {
            Authentication authentication = authenticate(loginDto.getEmail(), loginDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            boolean hasExpectedRole = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals(expectedRole));

            if (!hasExpectedRole) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have the expected role: " + expectedRole);
            }

            String token = jwtTokenUtil.generateToken(userDetails.getUsername(), expectedRole);
            JwtResponse jwtResponse = new JwtResponse(token);
            jwtResponse.setEmail(userDetails.getUsername());
            jwtResponse.setRole(expectedRole);

            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    private Authentication authenticate(String email, String password) throws Exception {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
