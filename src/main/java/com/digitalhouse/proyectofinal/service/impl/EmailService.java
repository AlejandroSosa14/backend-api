package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.exception.EmailSendingException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.UserRepository;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public void sendEmail(String to, String subject, String body) throws MessagingException {

        MimeMessage mg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mg);

        helper.setTo(to);
        helper.setText(body, true);
        helper.setSubject(subject);
        mailSender.send(mg);

    }

    public void forwardEmail(String email){
        try {

            Optional<UserEntity> user = userRepository.findByEmail(email);

            if (user.isEmpty()){
                throw new ResourceNotFoundException("User not found");
            }

            UserEntity userFound = user.get();

            String subject = "Welcome to Our Service, " + userFound.getName() + "!";

            String message = """
                Hi %s,
        
                Welcome to our platform! Your account has been successfully created.
                
                Here are your details:
                - Email: %s
                - Account Status: Active
                
                Please keep your credentials safe. If you did not register this account, contact support immediately.
                
                Link for login: http://localhost:8080/login
        
                Best regards,
                The Team
                """.formatted(userFound.getName(), userFound.getEmail());

            this.sendEmail(email, subject, message);

        } catch (MessagingException me) {
            throw new EmailSendingException("Failed to send welcome email. Please check email service.");
        }
    }

}
