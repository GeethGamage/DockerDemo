package com.stackstitch.docker.controller;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private  UserDto userDto;

    @BeforeEach
    public void setUp(){
        userDto = UserDto.builder()
                .id(1L)
                .firstName("Geeth")
                .lastName("Gamage")
                .address("Helsingborg, Sweden")
                .telephoneNo("0720400780")
                .build();
    }

//    @Test
//    public void testGetAllUsers(){
//        List<UserDto> userDtoList = new ArrayList<>();
//        userDtoList.add(userDto);
//        Mockito.when(userService.getUsers()).thenReturn(ResponseEntity.st);
//
//    }

    @Test
    public void testGetUserById() throws Exception {
//        UserDto mockUser = new UserDto(1L, "Geeth", "Gamage", "Helsingborg, Sweden", "0720400780");
//        Mockito.when(userService.getUserById(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(mockUser));
        ResultActions result = mockMvc.perform(get("/user/getUsers/{id}", 2));

        result.andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Geeth"))
                .andExpect(jsonPath("$.lastName").value("Gamage"))
                .andExpect(jsonPath("$.address").value("Helsingborg, Sweden"))
                .andExpect(jsonPath("$.telephoneNo").value("0720400780"));

    }

    @Test
    public void testAddUser() throws Exception {


        String json = """
                {
                  "firstName": "Geeth",
                  "lastName": "Gamage",
                  "address": "Helsingborg,Sweden",
                  "telephoneNo": "0770836675"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteUser() throws Exception {
        //Mockito.when(userService.deleteUser(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/removeUser/{id}", 1L))
                .andExpect(status().isOk());
    }


}
