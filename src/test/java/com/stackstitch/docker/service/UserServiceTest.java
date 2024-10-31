package com.stackstitch.docker.service;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.mapper.EntityToDto;
import com.stackstitch.docker.repository.UserRepository;
import com.stackstitch.docker.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp(){
        user = User.builder()
                .id(1L)
                .firstName("Geeth")
                .lastName("Gamage")
                .address("Helsingborg, Sweden")
                .telephoneNo("0720400780")
                .build();
    }

    @Test
    public void testGetAllUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(user);

        //Precondition
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //Action
        List<UserDto> result = userService.getUsers();

        //Verify
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testGetUserById(){
        //Precondition
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //Action
        UserDto result = userService.getUserById(user.getId());

        //Verify
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testAddUser(){
        //Precondition
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //Action
        UserDto result = userService.getUserById(user.getId());

        //Verify
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testDelete(){
        //Precondition
       Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //Action
        userService.deleteUser(1L);

        //Verify
        verify(userRepository, times(1)).delete(user);
    }


}
