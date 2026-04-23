package com.jakkas.langlearn.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jakkas.langlearn.restclient.UsersRestClient;
import jakarta.servlet.http.HttpServletRequest;

public class LoginControllerTests {
    @Mock
    private UsersRestClient restClient;

    @Mock
    private RedirectAttributes redirectAttributes; 

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private LoginController loginController;

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
    public void testHandleLoginInvalid() {
        //given
        when(restClient.authenticate(anyString(), anyString())).thenReturn(false);
        
        //when
        String response = loginController.handleLogin("", "", request, redirectAttributes);

        //then
        assertEquals("redirect:/login?error", response);
        
        verify(redirectAttributes).addFlashAttribute(anyString(), anyString());
    }
}