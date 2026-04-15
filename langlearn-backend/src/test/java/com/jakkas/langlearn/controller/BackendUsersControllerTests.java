package com.jakkas.langlearn.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jakkas.langlearn.model.User;
import com.jakkas.langlearn.repository.UsersRepository;
import com.jakkas.langlearn.service.UsersService;

public class BackendUsersControllerTests {
    
    @Mock
    private UsersService usersService;
    @Mock
    private UsersRepository usersRepository;


    @InjectMocks
    private BackendUsersController backendUsersController;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    AutoCloseable closeable;

    @BeforeEach
    void before() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void after() throws Exception {
        closeable.close();
    }


    @Test 
    public void testGetAllUsers() {
        //when
        List<User> allUsers = backendUsersController.getAllUsers();

        //then
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserByUsernameNoUser() {
        //given
        String username = "jakk";
        String password = "kuba123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(user));
    
        //when
        ResponseEntity<User> response = backendUsersController.getUserByUsername(username);


        //then
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserByUsernameExistUser() {
        //given
        String username = "kuba";
        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        //when
        ResponseEntity<User> response = backendUsersController.getUserByUsername(username);


        //then
        assertFalse(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
    }

    @Test
    public void testLoginProcessCorrectLogin() {
        //given
        User loginData = new User();
        loginData.setUsername("jakk");
        loginData.setPassword("kuba123");

        when(usersService.checkLogin(loginData.getUsername(), loginData.getPassword())).thenReturn(true);

        //when
        ResponseEntity<Void> response = backendUsersController.loginProcess(loginData);

        //then
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
    }

    @Test
    public void testLoginProcessIncorrectLogin() {
        //given
        User loginData = new User();
        loginData.setUsername("jakk");
        loginData.setPassword("kuba123");

        when(usersService.checkLogin(loginData.getUsername(), loginData.getPassword())).thenReturn(false);

        //when
        ResponseEntity<Void> response = backendUsersController.loginProcess(loginData);

        //then
        assertFalse(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
    }
}