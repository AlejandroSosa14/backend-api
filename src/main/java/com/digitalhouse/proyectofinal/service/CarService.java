package com.digitalhouse.proyectofinal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Page<CarEntity> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return  carRepository.findAll(pageable);

    }

    public CarEntity getById(Long id) {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        return car.get();
    }

    public Page<CarEntity> findByTransmission(String transmission, Pageable pageable) {

        Page<CarEntity> cars = carRepository.findByTransmissionType(transmission,pageable);

        if (cars.isEmpty()) {
            throw new RuntimeException("Cars not found");
        }

        return cars;

    }

    public CarEntity update(String serialNumber, CarEntity carEntity){

        Optional<CarEntity> carFound = carRepository.findBySerialNumber(serialNumber);

        if (carFound.isEmpty()){
            throw new RuntimeException("Cars not found");
        }

        carRepository.save(carEntity);
        return carEntity;
    }

    public void deleteById(Long id) {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        carRepository.deleteById(id);

    }

    public CarEntity create(CarEntity carEntity) {
        return carRepository.save(carEntity);
    }

}
