package com.stackstitch.docker.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.mapper.DtoToEntity;
import com.stackstitch.docker.repository.UserRepository;
import com.stackstitch.docker.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Sql(statements = "INSERT INTO user(id, first_name, last_name, address,telephone_no) VALUES (22,'Geeth', 'Gamage', 'Sweden', '0112192982')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM user WHERE id='22'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUsersList() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<User>> response = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });
        List<User> userList = response.getBody();
        assertThat(userList).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(userList.size(), userService.getUsers().size());
        assertEquals(userList.size(), userRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO user(id, first_name, last_name, address,telephone_no) VALUES (22,'Geeth', 'Gamage', 'Sweden', '0112192982')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM user WHERE id='22'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetUserById() throws JsonProcessingException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<UserDto> response = restTemplate.exchange(
                createURLWithPort() + "/22", HttpMethod.GET, entity, UserDto.class);
        UserDto user = response.getBody();
        assertThat(user).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(user, userService.getUserById(22L));
        assertEquals(user.getFirstName(), userService.getUserById(22L).getFirstName());
        assertEquals(DtoToEntity.mapUserDtoToUser(user), userRepository.findById(22L).orElse(null));
    }


    @Test
    @Sql(statements = "DELETE FROM user WHERE id='2'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAddUser() throws JsonProcessingException {
        UserDto userDto = new UserDto(2L, "Geeth", "Gamage", "Sweden", "0112192982");
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(userDto), headers);

        ResponseEntity<UserDto> response = restTemplate.exchange(
                createURLWithPort(), HttpMethod.POST, entity, UserDto.class);
        UserDto user = response.getBody();
        assertThat(user).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        //    assertEquals(user, userService.addUser(userDto));
        assertEquals(user.getFirstName(), userService.addUser(userDto).getFirstName());
        assertEquals(user.getFirstName(), userRepository.save(DtoToEntity.mapUserDtoToUser(userDto)).getFirstName());
    }


    @Test
    @Sql(statements = "INSERT INTO user(id, first_name, last_name, address,telephone_no) VALUES (22,'Geeth', 'Gamage', 'Sweden', '0112192982')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM user WHERE id='22'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteUser() throws JsonProcessingException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort() + "/22", HttpMethod.DELETE, entity, String.class);
        String userRes = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(userRes);
        assertEquals(userRes, "User Deleted Successfully");
    }


    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/user";
    }
}
