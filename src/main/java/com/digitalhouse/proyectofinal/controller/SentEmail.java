package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.exception.EmailSendingException;
import com.digitalhouse.proyectofinal.service.impl.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SentEmail {

    private final EmailService emailService;

    @GetMapping("api/email/{email}")
    public void sent(@PathVariable String email){
        emailService.forwardEmail(email);
    }

}
