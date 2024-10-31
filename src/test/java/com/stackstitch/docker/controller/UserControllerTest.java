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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .firstName("Geeth")
                .lastName("Gamage")
                .address("Helsingborg, Sweden")
                .telephoneNo("0720400780")
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {

        //Precondition
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        Mockito.when(userService.getUsers()).thenReturn(userDtoList);

        //Action
        ResultActions result = mockMvc.perform(get("/api/user"));

        //verify
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Geeth"))
                .andExpect(jsonPath("$[0].lastName").value("Gamage"))
                .andExpect(jsonPath("$[0].address").value("Helsingborg, Sweden"))
                .andExpect(jsonPath("$[0].telephoneNo").value("0720400780"));

    }

    @Test
    public void testGetUserById() throws Exception {

        //Precondition
        Mockito.when(userService.getUserById(1L)).thenReturn(userDto);

        //action
        ResultActions result = mockMvc.perform(get("/api/user/{id}", 1));

        //verify
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Geeth"))
                .andExpect(jsonPath("$.lastName").value("Gamage"))
                .andExpect(jsonPath("$.address").value("Helsingborg, Sweden"))
                .andExpect(jsonPath("$.telephoneNo").value("0720400780"));
    }

    @Test
    public void testAddUser() throws Exception {

        //Precondition
        Mockito.when(userService.addUser(any(UserDto.class))).thenReturn(userDto);

        String json = """
                {
                  "id" : 1,
                  "firstName": "Geeth",
                  "lastName": "Gamage",
                  "address": "Helsingborg,Sweden",
                  "telephoneNo": "0770836675"
                }
                """;
        //action
        ResultActions result = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        //verify
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.address").value(userDto.getAddress()))
                .andExpect(jsonPath("$.telephoneNo").value(userDto.getTelephoneNo()));
    }

    @Test
    public void testDeleteUser() throws Exception {


        //Action
        ResultActions result = mockMvc.perform
                (MockMvcRequestBuilders.delete("/api/user/{id}", 1L));
        //Verify
        result.andExpect(status().isOk())
                .andDo(print());
    }


}
