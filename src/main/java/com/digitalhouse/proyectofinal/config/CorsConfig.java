package com.digitalhouse.proyectofinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**") // Permitir acceso a todas las rutas que comienzan con /api/
//                        .allowedOrigins("http://localhost:5173") // Permitir acceso desde el frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
//                        .allowedHeaders("*") // Permitir cualquier encabezado
//                        .allowCredentials(true); // Permitir credenciales (ej. cookies, auth headers)
//            }
//        };
//    }
}
