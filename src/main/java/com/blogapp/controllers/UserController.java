package com.blogapp.controllers;

import com.blogapp.payloads.UserDto;
import com.blogapp.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    // POST - create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.CREATED);
    }

    //
}
