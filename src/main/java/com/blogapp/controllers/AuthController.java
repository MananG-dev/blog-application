package com.blogapp.controllers;

import com.blogapp.payloads.JwtAuthRequest;
import com.blogapp.payloads.JwtAuthResponse;
import com.blogapp.security.CustomUserDetailService;
import com.blogapp.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        System.out.println("[AuthController] Authenticating user: " + jwtAuthRequest.getUsername());
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtAuthRequest.getUsername());
        System.out.println("[AuthController] User details loaded: " + userDetails.getUsername() + " with authorities: " + userDetails.getAuthorities());
        String token = this.jwtTokenHelper.generateToken(jwtAuthRequest.getUsername());
        System.out.println("Generated JWT Token: " + token);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
     }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch(DisabledException e) {
            throw new RuntimeException("User is disabled");
        } catch(BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or password");
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
