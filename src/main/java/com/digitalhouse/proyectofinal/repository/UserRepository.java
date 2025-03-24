package com.digitalhouse.proyectofinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhouse.proyectofinal.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByNameAndActive(String name, Boolean active);

    public Optional<UserEntity> findByEmail(String email);

    public Optional<UserEntity> findByName(String username);

    @Query("SELECT u.id FROM UserEntity u WHERE u.name = ?1")
    public Long findIdByName(String username);

    public Long countByTypeContains(String type);
}
