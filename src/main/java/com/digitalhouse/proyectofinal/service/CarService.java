package com.digitalhouse.proyectofinal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<CarEntity> getAll() {

        List<CarEntity> cars = carRepository.findAll();

        if (cars.isEmpty()) {
            throw new RuntimeException("Cars not found");
        }

        return cars;
    }

    public CarEntity getById(Long id) {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        return car.get();
    }

    public List<CarEntity> findByTransmission(String transmission){

        List<CarEntity> cars = carRepository.findByTransmissionType(transmission);

        if (cars.isEmpty()) {
            throw new RuntimeException("Cars not found");
        }

        return cars;

    }

    public void deleteById(Long id) {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        carRepository.deleteById(id);

    }

}
