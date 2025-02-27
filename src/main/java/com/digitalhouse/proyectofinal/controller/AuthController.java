package com.digitalhouse.proyectofinal.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/api/login")
    public Map<String, Object> login(@AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> response = new HashMap<>();

        if (userDetails == null) {
            response.put("message", "Unauthorized");
        }

        response.put("message", "Successful login");
        response.put("user", userDetails.getUsername());
        response.put("authorities", userDetails.getAuthorities());

        return response;

    }

}
