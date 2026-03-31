package com.jakkas.langlearn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable()) // KLUCZOWE: Wyłącz CSRF, inaczej każdy POST bez tokena jest odrzucany po cichu
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/process-login", "/css/**").permitAll() // Musi tu być!
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            // loginProcessingUrl musi być INNY niż Twój kontroler, 
            // żeby Spring nie próbował sam logować
            .loginProcessingUrl("/do-not-use-this-url") 
            .permitAll()
        );
    
        return http.build();
    }
}