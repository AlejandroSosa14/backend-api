package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    public UserEntity getById(Long id){

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        return user.get();
    }

    public UserEntity create(UserEntity userEntity){

        String passwordEncode = passwordEncoder.encode(userEntity.getPassword());

        userEntity.setPassword(passwordEncode);

        return userRepository.save(userEntity);
    }

    public UserEntity update(Long id, UserEntity userEntity){

        Optional<UserEntity> userFound = userRepository.findById(id);

        if (userFound.isEmpty()){
            throw new RuntimeException("User not found");
        }

        String passwordEncode = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncode);

        userRepository.save(userEntity);

        return userEntity;

    }

    public void deleteById(Long id) {

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);

    }

}
