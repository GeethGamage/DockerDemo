package com.stackstitch.docker.controller;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<Object> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @DeleteMapping("/removeUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);

    }

}
