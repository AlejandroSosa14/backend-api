package com.digitalhouse.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import java.util.List;


public interface CarRepository extends JpaRepository<CarEntity,Long>{

    public List<CarEntity> findByTransmissionType(String transmissionType);

}
