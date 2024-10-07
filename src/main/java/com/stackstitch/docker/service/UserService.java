package com.stackstitch.docker.service;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<Object> getUsers();

    public ResponseEntity<Object> addUser(UserDto userDto);

    public ResponseEntity<Object> deleteUser(Long userId);
}
