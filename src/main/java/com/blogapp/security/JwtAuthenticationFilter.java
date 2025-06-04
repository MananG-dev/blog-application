package com.blogapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.blogapp.utils.AppConstants.Authorization;
import static com.blogapp.utils.AppConstants.Bearer;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader(Authorization);
        String username = null;
        String token = null;
        System.out.println("[JwtAuthenticationFilter] Processing request for: " + request.getRequestURI() + " and the Authorization header: " + requestToken);

        if (requestToken != null && requestToken.startsWith(Bearer)) {
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
                System.out.println("[JwtAuthenticationFilter] Extracted username from token: " + username);
            } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("JWT token does not begin with Bearer String");
        }

        // Once we have the token, we can validate it and set the authentication in the context
        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // If the token is valid, set the authentication in the security context
                System.out.println("[JwtAuthenticationFilter] Valid JWT token for user: " + userDetails.getUsername() + " password: " + userDetails.getPassword() + " authorities: " + userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("JWT token is not valid");
            }
        } else {
            System.out.println("Username is null or context authentication is not null");
        }

        filterChain.doFilter(request, response);
    }
}
