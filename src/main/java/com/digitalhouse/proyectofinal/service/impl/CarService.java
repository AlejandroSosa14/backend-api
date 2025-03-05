package com.digitalhouse.proyectofinal.service.impl;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService {

    private final CarRepository carRepository;
    private final CategoryService categoryService;
    private final FileUploadService fileUploadService;

    public Page<CarEntity> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return carRepository.findAll(pageable);

    }

    public CarEntity getById(Long id) throws ResourceNotFoundException {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            //throw new RuntimeException("Car not found");
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        return car.get();
    }

    public Page<CarEntity> findByTransmission(String transmission, Pageable pageable) throws ResourceNotFoundException {

        Page<CarEntity> cars = carRepository.findByTransmissionType(transmission, pageable);

        if (cars.isEmpty()) {
            //throw new RuntimeException("Cars not found");
            throw new ResourceNotFoundException("No cars found with transmission type: " + transmission);
        }

        return cars;

    }

    public CarEntity update(Long id, CarEntity carEntity, List<MultipartFile> files) throws ResourceNotFoundException {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            //throw new RuntimeException("Car not found");
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        try {

            String filesUpload = fileUploadService.uploadFiles(files);
            carEntity.setImages(filesUpload);

            CarEntity carFound = car.get();
            carFound.setSerialNumber(carEntity.getSerialNumber());
            carFound.setBrand(carEntity.getBrand());
            carFound.setName(carEntity.getName());
            carFound.setModel(carEntity.getModel());
            carFound.setStatus(carEntity.getStatus());
            carFound.setFuelType(carEntity.getFuelType());
            carFound.setTransmissionType(carEntity.getTransmissionType());
            carFound.setReserveCost(carEntity.getReserveCost());
            carFound.setImages(filesUpload);

            CategoryEntity category = categoryService.getById(carEntity.getCategory().getId());

            carFound.setCategory(category);

            carRepository.save(carFound);

            return carFound;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteById(Long id) throws ResourceNotFoundException {

        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            //throw new RuntimeException("Car not found");
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        carRepository.deleteById(id);

    }

    public CarEntity create(CarEntity carEntity, List<MultipartFile> files) {

        try {
            String filesUpload = fileUploadService.uploadFiles(files);
            carEntity.setImages(filesUpload);
            return carRepository.save(carEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
