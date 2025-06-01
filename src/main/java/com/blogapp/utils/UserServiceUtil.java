package com.blogapp.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserServiceUtil {
    public static String hashPassword(String password) {
        // Implement password hashing logic here
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashpw; // Placeholder, replace with actual hashing logic
    }
}
