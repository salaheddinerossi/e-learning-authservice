package com.example.authservice.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String email = null;

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                email = jwtTokenUtil.extractEmail(jwtToken);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtTokenUtil.validateToken(jwtToken, email)) {
                    setAuthenticationContext(email, jwtToken, request);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token");
        } catch (MalformedJwtException e) {
            logger.info("Malformed JWT token");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Malformed JWT token");
        } catch (SignatureException e) {
            logger.info("Invalid JWT signature");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
        } catch (JwtException e) {
            logger.info("JWT token validation failed");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token validation failed");
        }

        chain.doFilter(request, response);
    }

    private void setAuthenticationContext(String email, String jwtToken, HttpServletRequest request) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        String role = jwtTokenUtil.extractRole(jwtToken);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.toUpperCase()));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
