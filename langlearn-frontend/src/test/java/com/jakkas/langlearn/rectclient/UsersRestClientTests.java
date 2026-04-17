package com.jakkas.langlearn.rectclient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.jakkas.langlearn.dto.User;
import com.jakkas.langlearn.restclient.UsersRestClient;

public class UsersRestClientTests {
    @Mock
    private RestClient restClient;

    @InjectMocks
    private UsersRestClient usersRestClient;


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
    public void testGetUserDetails() {
        //given
        String username = "jakk";
        User user = new User();
        user.setUsername(username);
        user.setPassword("kuba123");

        // given
        RestClient.RequestHeadersUriSpec getSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(getSpec);
        when(getSpec.uri("/api/users/{username}", username)).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(User.class))
                .thenReturn(user);
        
        //when
        User result = usersRestClient.getUserDetails(username);

        //then
        assertNotNull(result);
        verify(restClient, times(1)).get();
    }


    @Test
    public void testAuthenticationIsSuccessful() {
        // given
        RestClient.RequestBodyUriSpec postSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.post()).thenReturn(postSpec);
        when(postSpec.uri("/api/process-login")).thenReturn(bodySpec);
        when(bodySpec.body(any(User.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity())
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // when
        boolean result = usersRestClient.authenticate("jakk", "kuba123");

        // then
        assertTrue(result);
    }


    @Test
    public void testAuthenticationFailedWith401() {
        // given
        RestClient.RequestBodyUriSpec postSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.post()).thenReturn(postSpec);
        when(postSpec.uri("/api/process-login")).thenReturn(bodySpec);
        when(bodySpec.body(any(User.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity())
                .thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));


        //when
        boolean result = usersRestClient.authenticate("jakk", "kuba123");

        //then
        assertFalse(result);
    }

    @Test
    public void testAuthenticationCausesUnexpectedException() {
        // given
        RestClient.RequestBodyUriSpec postSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);

        when(restClient.post()).thenReturn(postSpec);
        when(postSpec.uri("/api/process-login")).thenReturn(bodySpec);
        when(bodySpec.body(any(User.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        //when
        boolean result = usersRestClient.authenticate("jakk", "kuba123");

        //then
        assertFalse(result);
    }
}
