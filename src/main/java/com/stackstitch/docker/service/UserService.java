package com.stackstitch.docker.service;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public UserDto getUserById(Long id);

    public List<UserDto> getUsers();

    public UserDto addUser(UserDto userDto);

    public void deleteUser(Long userId);
}
