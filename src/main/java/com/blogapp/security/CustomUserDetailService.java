package com.blogapp.security;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("[CustomUserDetailService] Loading user by email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", email));
        System.out.println("[CustomUserDetailService] User found: " + user.getName() + " with roles: " + user.getRoles());
        return user;
    }
}
