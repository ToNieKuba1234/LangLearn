package com.jakkas.langlearn.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.jakkas.langlearn.dto.User;
import com.jakkas.langlearn.restclient.UsersRestClient;

public class IndexControllerTests {
    @Mock
    private UsersRestClient usersRestClient;
    @Mock
    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @InjectMocks
    private IndexController indexController;


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
    public void testIndexAvailableUser() {
        //given
        Model model = new ExtendedModelMap();
        User user = new User();
        user.setUsername("jakk");
        user.setTrophies(116);

        when(usersRestClient.getUserDetails(any())).thenReturn(user);


        //when
        String result = indexController.index(model);

        //then
        assertEquals(user.getUsername(), model.getAttribute("username"));
        assertEquals(user.getTrophies(), model.getAttribute("trophies"));
    }

    @Test
    public void testIndexUnAvailableUser() {
        //given
        Model model = new ExtendedModelMap();

        when(usersRestClient.getUserDetails(any())).thenReturn(null);

        //then
        assertThrows(NoSuchElementException.class, () -> {
            //when
            indexController.index(model); 
        });
    }
}