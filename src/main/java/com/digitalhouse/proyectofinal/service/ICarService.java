package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICarService {
    Page<CarEntity> getAll (int page, int size);
    CarEntity getById (Long id);
    Page<CarEntity> findByTransmission(String transmission, Pageable pageable);
    CarEntity update(Long id, CarEntity carEntity);
    void deleteById(Long id);
    CarEntity create(CarEntity carEntity);


}
