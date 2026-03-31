package com.jakkas.langlearn.restclient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.jakkas.langlearn.dto.Users;

@Component
public class UsersRestClient {
    private final RestClient restClient;

    public UsersRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean authenticate(String username, String password) {
        try {
            Users userPayload = new Users();
            userPayload.setUsername(username);
            userPayload.setPassword(password);

            ResponseEntity<Void> response = restClient.post()
                    .uri("/api/process-login")
                    .body(userPayload)
                    .retrieve()
                    .toBodilessEntity();
            
            System.out.println("DEBUG: Backend status response : " + response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            System.err.println("DEBUG : Backend response error : " + e.getMessage());
            return false;
        }
    }

    public Users getUserDetails(String username) {
        try {
            return restClient.get().uri("/api/users/{username}", username).retrieve().body(Users.class);
        } catch (Exception e) {
            System.err.println("Error occured while downloading user data : " + e.getMessage());
            return null;
        }
    }
}
