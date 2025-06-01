package com.blogapp.services.impl;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;
import com.blogapp.utils.UserServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(UserServiceUtil.hashPassword(userDto.getPassword()));
        User user = this.userDtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        foundUser.setName(userDto.getName());
        foundUser.setEmail(userDto.getEmail());
        foundUser.setPassword(UserServiceUtil.hashPassword(userDto.getPassword()));
        foundUser.setAbout(userDto.getAbout());
        User updatedUser = userRepository.save(foundUser);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUser(Integer userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        return this.userToUserDto(foundUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map((u) -> this.userToUserDto(u)).toList();
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        userRepository.delete(foundUser);
    }

    public User userDtoToUser(UserDto userDto) {
        User user = User.builder().name(userDto.getName()).email(userDto.getEmail()).password(userDto.getPassword()).about(userDto.getAbout()).build();
        return user;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = UserDto.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).password(user.getPassword()).about(user.getAbout()).build();
        return userDto;
    }
}
