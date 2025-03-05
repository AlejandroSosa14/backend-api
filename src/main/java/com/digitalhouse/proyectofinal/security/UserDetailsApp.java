package com.digitalhouse.proyectofinal.security;


import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsApp implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) {

        UserEntity userFound = userRepository.findByNameAndActive(
                name, Boolean.TRUE).orElseThrow(() -> new RuntimeException("User not found")
        );

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userFound.getType());

        return new User(userFound.getName(), userFound.getPassword(), List.of(authority));
    }
}
