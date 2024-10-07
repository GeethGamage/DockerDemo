package com.stackstitch.docker.service.Impl;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.mapper.DtoToEntity;
import com.stackstitch.docker.mapper.EntityToDto;
import com.stackstitch.docker.repository.UserRepository;
import com.stackstitch.docker.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<Object> getUsers() {
        try {
            List<UserDto> userList = userRepository.findAll().stream()
                    .map(EntityToDto::mapUserToUserDto).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public ResponseEntity<Object> addUser(UserDto userDto){
        try{
            userRepository.save(DtoToEntity.mapUserDtoToUser(userDto));
            return ResponseEntity.status(HttpStatus.OK).body("User Added Successfully");
        }catch(Exception ex){
            log.error(ex.getMessage());
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
            log.error(ex.getMessage());
            throw ex;
        }
    }


}
