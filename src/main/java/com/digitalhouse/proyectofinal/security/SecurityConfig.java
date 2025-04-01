package com.digitalhouse.proyectofinal.security;

import com.digitalhouse.proyectofinal.filter.JwtGeneratorFilter;
import com.digitalhouse.proyectofinal.filter.JwtValidatorFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${url.frontend}")
    private String urlFrontend;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //http.addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class);
        //http.addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class);

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso sin autenticación a Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/email/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/user/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/upload/**").hasRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/login").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/search/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/post/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/cars").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/users/favorites").hasAnyRole("admin","customer")
                        .requestMatchers(HttpMethod.GET, "/api/users/favorites/*").hasAnyRole("admin","customer")
                        .requestMatchers(HttpMethod.PUT, "/api/users/favorites/**").hasAnyRole("admin","customer")
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/api/reserves").hasAnyRole("admin","customer")
                        .requestMatchers(HttpMethod.GET, "/api/reserves").hasAnyRole("admin")
                        .requestMatchers(HttpMethod.GET, "/api/reserves/**").hasAnyRole("admin","customer")
                        .requestMatchers(HttpMethod.GET, "/api/reserves/user/**").hasAnyRole("admin","customer")
                ) .httpBasic(Customizer.withDefaults());
                /*.httpBasic(httpBasic -> {
                });*/

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); // Permitir todas las solicitudes de cualquier origen
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permitir cualquier encabezado
        configuration.setAllowCredentials(true); // Permitir credenciales (cookies, Authorization header, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar configuración a todas las rutas
        return source;
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
