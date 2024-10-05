package com.stackstitch.docker.service.Impl;

import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.repository.UserRepository;
import com.stackstitch.docker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<Object> getUsers() {
        try {
            List<User> userList = userRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public ResponseEntity<Object> addUser(User user){
        try{
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User Added Successfully");
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    public ResponseEntity<Object> deleteUser(Long id){
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                userRepository.delete(user.get());
                return ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id not found");
            }

        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }


}
