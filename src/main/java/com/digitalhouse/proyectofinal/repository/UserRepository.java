package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity,Long> {

    public Optional<UserEntity> findByNameAndActive(String name, Boolean active);
    public  Optional<UserEntity> findByEmail(String email);
}
