package com.stackstitch.docker.repository;


import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.exception.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.save(new User(1L, "Geeth", "Gamage", "Sweden", "0112192982"));
        userRepository.save(new User(2L, "Hashini", "Perera", "Sweden", "011289289"));
    }

    @AfterEach
    public void destroy() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = userRepository.findAll();

        //Verify
        Assertions.assertNotNull(userList);
       // Assertions.assertEquals(2, userList.size());
    }

    @Test
    public void saveUser(){
        User user  = new User(null, "Geeth", "Gamage", "Sweden", "0112192982");
        User savedUser = userRepository.save(user);
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    public void getInvalidUser(){

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(113L).get();
        });


        Assertions.assertNotNull(exception);
        Assertions.assertTrue(exception.getClass().equals(NoSuchElementException.class));
        Assertions.assertTrue(exception.getMessage().contains("No value present"));
    }

    @Test
    public void deleteUser(){
        userRepository.deleteById(1L);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(1L).get();
        });
        Assertions.assertNotNull(exception);
        Assertions.assertTrue(exception.getClass().equals(NoSuchElementException.class));
        Assertions.assertTrue(exception.getMessage().contains("No value present"));
        assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
    }

}
