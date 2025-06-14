package com.blogapp.services;

import com.blogapp.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUser(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
