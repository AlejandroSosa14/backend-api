package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.FavoriteRequest;
import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface IUserService {
    List<UserEntity> getAllUser();
    UserEntity getById(Long id);
    UserEntity create(UserEntity userEntity);
    UserEntity update(Long id, UserEntity userEntity);
    void deleteById(Long id);
    void setFavorites(FavoriteRequest favoriteRequest);
    Set<CarEntity> getFavorites(String username);
    void updateFavorites(String username, Long id);
    UserEntity findByName(String name);
    void setScore(String username, Long idCar, Integer score);

}
