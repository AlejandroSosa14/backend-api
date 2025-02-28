package com.digitalhouse.proyectofinal.service.impl;

import java.util.Optional;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.service.ICarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService {

    private final CarRepository carRepository;
    private final CategoryService categoryService;

    public Page<CarEntity> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return  carRepository.findAll(pageable);

    }

    public CarEntity getById(Long id) throws ResourceNotFoundException {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            //throw new RuntimeException("Car not found");
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        return car.get();
    }

    public Page<CarEntity> findByTransmission(String transmission, Pageable pageable) throws ResourceNotFoundException  {

        Page<CarEntity> cars = carRepository.findByTransmissionType(transmission,pageable);

        if (cars.isEmpty()) {
            //throw new RuntimeException("Cars not found");
            throw new ResourceNotFoundException("No cars found with transmission type: " + transmission);
        }

        return cars;

    }

    public CarEntity update(Long id, CarEntity carEntity) throws ResourceNotFoundException {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()){
            //throw new RuntimeException("Car not found");
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        CarEntity carFound = car.get();
        carFound.setSerialNumber(carEntity.getSerialNumber());
        carFound.setBrand(carEntity.getBrand());
        carFound.setName(carEntity.getName());
        carFound.setModel(carEntity.getModel());
        carFound.setStatus(carEntity.getStatus());
        carFound.setFuelType(carEntity.getFuelType());
        carFound.setTransmissionType(carEntity.getTransmissionType());
        carFound.setReserveCost(carEntity.getReserveCost());
        carFound.setImages(carEntity.getImages());

        CategoryEntity category = categoryService.getById(carEntity.getCategory().getId());

        carFound.setCategory(category);

        carRepository.save(carFound);

        return carFound;
    }

    public void deleteById(Long id) throws ResourceNotFoundException {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            //throw new RuntimeException("Car not found");
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        carRepository.deleteById(id);

    }

    public CarEntity create(CarEntity carEntity) {
        return carRepository.save(carEntity);
    }

}
