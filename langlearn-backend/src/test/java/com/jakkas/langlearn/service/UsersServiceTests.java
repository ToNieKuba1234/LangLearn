package com.jakkas.langlearn.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jakkas.langlearn.model.User;
import com.jakkas.langlearn.repository.UsersRepository;

public class UsersServiceTests {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AutoCloseable closeable;

    @BeforeEach
    void before() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void after() throws Exception{
        closeable.close();
    }

    @Test
    public void testCheckLoginForAvailableUser() {
        //given 
        String username = "jakk";
        String password = "kuba123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        when(usersRepository.findByUsername(any())).thenReturn(Optional.of(user));

        //when
        boolean result = usersService.checkLogin(username, password);

        //then
        assertTrue(result);
    }

    @Test
    public void testCheckLoginForUnavailableUser() {
        //given 
        String username = "jakk";
        String password = "kuba123";
        when(usersRepository.findByUsername(any())).thenReturn(Optional.empty());

        //when
        boolean result = usersService.checkLogin(username, password);

        //then
        assertFalse(result);
    }
}