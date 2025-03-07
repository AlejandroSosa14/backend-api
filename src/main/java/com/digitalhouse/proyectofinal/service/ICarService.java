package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICarService {
    Page<CarEntity> getAll (int page, int size);
    CarEntity getById (Long id);
    Page<CarEntity> findByTransmission(String transmission, Pageable pageable);
    CarEntity update(Long id, CarEntity carEntity, List<MultipartFile> files, List<String> removedImages);
    void deleteById(Long id);
    CarEntity create(CarEntity carEntity, List<MultipartFile> files) throws IOException;


}
