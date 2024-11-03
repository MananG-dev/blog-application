package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    // POST - create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.CREATED);
    }

    // PUT - update User
    @PutMapping("/{userID}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable(name = "userID") Integer userId) {
        UserDto userDto1 = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    // GET - get User
    @GetMapping("/{userID}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "userID") Integer uid) {
        UserDto userDto = userService.getUser(uid);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    // GET - get List of Users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    // DELETE - delete user
    @DeleteMapping("/{userID}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name = "userID") Integer uid) {
        userService.deleteUser(uid);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("User Deleted Successfully")
                .localDateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .status(true)
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}
