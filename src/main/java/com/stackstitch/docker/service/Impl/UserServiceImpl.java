package com.stackstitch.docker.service.Impl;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.exception.CustomException;
import com.stackstitch.docker.mapper.DtoToEntity;
import com.stackstitch.docker.mapper.EntityToDto;
import com.stackstitch.docker.repository.UserRepository;
import com.stackstitch.docker.service.MathOperation;
import com.stackstitch.docker.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;




    public UserDto getUserById(Long id) {
        try {
            MathOperation add = (a,b) -> a + b;
            MathOperation mul = (a,b) -> a * b;

            int x = add.operate(10 , 20);

            CompletableFuture<User> s = CompletableFuture.supplyAsync(() -> {
                return userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));
            });
            CompletableFuture<Void> dto = s.thenAccept(result -> EntityToDto.mapUserToUserDto(result));
            User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));
            return EntityToDto.mapUserToUserDto(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public List<UserDto> getUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(EntityToDto::mapUserToUserDto).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public UserDto addUser(UserDto userDto) {
        try {
            User user = userRepository.save(DtoToEntity.mapUserDtoToUser(userDto));
            return EntityToDto.mapUserToUserDto(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User id not found"));
            userRepository.delete(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }


}
