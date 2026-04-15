package com.jakkas.langlearn.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    public void testRegisterUser() {
        //given
        String username = "jakk";
        String password = "kuba123";
        ArgumentCaptor<User> usersCaptor = ArgumentCaptor.forClass(User.class);

        //when
        when(usersRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User returnedValue = usersService.registerUser(username, password);

        //then
        verify(usersRepository, times(1)).save(usersCaptor.capture());
        User resultUser = usersCaptor.getValue();

        assertEquals(username, resultUser.getUsername());
        assertTrue(passwordEncoder.matches(password, resultUser.getPassword()));
        assertEquals(username, returnedValue.getUsername());
        assertTrue(passwordEncoder.matches(password, resultUser.getPassword()));
    }
}