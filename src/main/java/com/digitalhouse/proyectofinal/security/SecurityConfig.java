package com.digitalhouse.proyectofinal.security;

import com.digitalhouse.proyectofinal.filter.JwtGeneratorFilter;
import com.digitalhouse.proyectofinal.filter.JwtValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class);

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
                        .requestMatchers(HttpMethod.POST, "/api/user/authenticate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/login").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/cars").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("admin")
                )
                .httpBasic(httpBasic -> {
                });

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("administrador")
//                .password(passwordEncoder().encode("password"))
//                .roles("admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
