package com.digitalhouse.proyectofinal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface CarRepository extends JpaRepository<CarEntity, Long>, JpaSpecificationExecutor<CarEntity> {

    public Page<CarEntity> findByTransmissionType(String transmissionType, Pageable pageable);

    public Optional<CarEntity> findBySerialNumber(String serialNumber);

    public Page<CarEntity> findByPostDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    public Page<CarEntity> findByBrand(String brand, Pageable pageable);

    public Page<CarEntity> findByLocationCity(String location, Pageable pageable);

}
