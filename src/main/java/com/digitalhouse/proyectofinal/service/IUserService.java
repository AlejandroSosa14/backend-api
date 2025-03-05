package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> getAllUser();
    UserEntity getById(Long id);
    UserEntity create(UserEntity userEntity);
    UserEntity update(Long id, UserEntity userEntity);
    void deleteById(Long id);

}
