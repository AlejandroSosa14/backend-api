package com.digitalhouse.proyectofinal.service;

import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {

        MimeMessage mg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mg);

        helper.setTo(to);
        helper.setText(body, true);
        helper.setSubject(subject);
        mailSender.send(mg);

    }

}
