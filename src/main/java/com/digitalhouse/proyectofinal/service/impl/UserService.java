package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.exception.ConflictException;
import com.digitalhouse.proyectofinal.exception.EmailSendingException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.UserRepository;
import com.digitalhouse.proyectofinal.service.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    public UserEntity getById(Long id){

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()){
            //throw new RuntimeException("User not found");
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }

        return user.get();
    }

    public UserEntity create(UserEntity userEntity){
//
//        String passwordEncode = passwordEncoder.encode(userEntity.getPassword());
//
//        userEntity.setPassword(passwordEncode);
//
//        try {
//            emailService.sendEmail(userEntity.getEmail(), "test", "content");
//            return userRepository.save(userEntity);
//        }catch (MessagingException me){
//            throw new RuntimeException(me.getMessage());
//        }
        // Verificar si el correo ya está registrado
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new ConflictException("User with email " + userEntity.getEmail() + " already exists");
        }

        String passwordEncode = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncode);
        UserEntity savedUser = userRepository.save(userEntity);

        // Mensaje personalizado --> Mejorar
        String subject = "Welcome to Our Service, " + savedUser.getName() + "!";
        String message = """
        Hi %s,

        Welcome to our platform! Your account has been successfully created.
        
        Here are your details:
        - Email: %s
        - Account Status: Active
        
        Please keep your credentials safe. If you did not register this account, contact support immediately.

        Best regards,
        The Team
        """.formatted(savedUser.getName(), savedUser.getEmail());
        try {
            emailService.sendEmail(savedUser.getEmail(), subject, message);
        } catch (MessagingException me) {
            throw new EmailSendingException("User created, but failed to send welcome email. Please check email service.");
        }

        return savedUser;

    }

    public UserEntity update(Long id, UserEntity userEntity){

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()){
            //throw new RuntimeException("User not found");
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        //
        Optional<UserEntity> existingUser = userRepository.findByEmail(userEntity.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new ConflictException("Email " + userEntity.getEmail() + " is already in use by another user");
        }

        UserEntity userFound = user.get();
        userFound.setName(userEntity.getName());
        userFound.setEmail(userEntity.getEmail());

        String passwordEncode = passwordEncoder.encode(userEntity.getPassword());
        userFound.setPassword(passwordEncode);

        userFound.setType(userEntity.getType());
        userFound.setActive(userEntity.getActive());

        userRepository.save(userFound);

        return userFound;

    }

    public void deleteById(Long id) {

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            //throw new RuntimeException("User not found");
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }

        if (user.get().getType().contains("admin")){
           Long adminCount = userRepository.countByTypeContains("admin");

            if (adminCount <= 1) {
                throw new ConflictException("Cannot delete admin user. At least one admin user must exist.");
            }

        }

        userRepository.deleteById(id);

    }

}

