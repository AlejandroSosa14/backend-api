package com.digitalhouse.proyectofinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso sin autenticación a Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod. GET,"/cars/**").permitAll()
                        .requestMatchers(HttpMethod. DELETE,"/cars/**").hasRole("admin")
                        .requestMatchers(HttpMethod. POST,"/cars").hasRole("admin")
                        .requestMatchers(HttpMethod. PUT,"/cars/**").hasRole("admin")
                        .requestMatchers(HttpMethod. GET,"/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod. POST,"/users").hasRole("admin")
                        .requestMatchers(HttpMethod. PUT,"/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod. DELETE,"/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod. GET,"/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod. POST,"/categories").hasRole("admin")
                        .requestMatchers(HttpMethod. PUT,"/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod. DELETE,"/categories/**").hasRole("admin")
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("administrador")
                .password(passwordEncoder().encode("password"))
                .roles("admin")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
