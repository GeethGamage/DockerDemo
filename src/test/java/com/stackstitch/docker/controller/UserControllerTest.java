package com.stackstitch.docker.controller;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void testGetUserById() throws Exception {
        UserDto mockUser = new UserDto(1L, "Geeth", "Gamage", "Helsingborg, Sweden", "0720400780");
        Mockito.when(userService.getUserById(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/getUsers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Geeth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gamage"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Helsingborg, Sweden"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telephoneNo").value("0720400780"));
    }

    @Test
    public void testAddUser() throws Exception {
        UserDto userDto = new UserDto(1L, "Geeth", "Gamage", "Helsingborg, Sweden", "0720400780");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Gamage\", \"address\": \"Helsingborg\", \"telephoneNo\": \"0720400780\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/removeUser/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
