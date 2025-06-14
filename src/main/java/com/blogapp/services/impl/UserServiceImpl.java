package com.blogapp.services.impl;

import com.blogapp.entities.Roles;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.RoleDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.RoleRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;
import com.blogapp.utils.UserServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.blogapp.utils.AppConstants.NORMAL_USER;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = userDtoToUser(userDto);
        user.setPassword(UserServiceUtil.hashPassword(user.getPassword()));
        Roles role = roleRepository.findById(NORMAL_USER).get();
        Set<Roles> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return this.userToUserDto(savedUser);
    }

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
        return users.stream().map(this::userToUserDto).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        userRepository.delete(foundUser);
    }

    public User userDtoToUser(UserDto userDto) {
        return User.builder().name(userDto.getName()).email(userDto.getEmail()).password(userDto.getPassword()).about(userDto.getAbout()).build();
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = UserDto.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).password(user.getPassword()).about(user.getAbout()).build();
        Set<RoleDto> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            roles.add(RoleDto.builder().role(role.getRoleName()).build());
        });
        userDto.setRoles(roles);
        return userDto;
    }
}
