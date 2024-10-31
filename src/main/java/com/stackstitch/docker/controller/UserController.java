package com.stackstitch.docker.controller;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUsers(){
        return new ResponseEntity<>( userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>( userService.addUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new  ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
    }

}
