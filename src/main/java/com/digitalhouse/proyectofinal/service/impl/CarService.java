package com.digitalhouse.proyectofinal.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.service.ICarService;
import com.digitalhouse.proyectofinal.specification.CarSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public CarEntity update(Long id, CarEntity carEntity, String dir, List<MultipartFile> files, List<String> removedImages) throws ResourceNotFoundException {
        Optional<CarEntity> car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new ResourceNotFoundException("Car with ID " + id + " not found");
        }

        try {
            CarEntity carFound = car.get();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            List<String> currentImages = carFound.getImages() != null
                    ? objectMapper.readValue(carFound.getImages(), List.class)
                    : new ArrayList<>();

            if (removedImages != null) {
                currentImages.removeAll(removedImages);
            }

            if (files != null && !files.isEmpty()) {
                List<String> newFilesUploads = Collections.singletonList(fileUploadService.uploadFiles(dir, files));
                currentImages.addAll(newFilesUploads);
            }

            carFound.setImages(objectMapper.writeValueAsString(currentImages));

            carFound.setSerialNumber(carEntity.getSerialNumber());
            carFound.setBrand(carEntity.getBrand());
            carFound.setName(carEntity.getName());
            carFound.setModel(carEntity.getModel());
            carFound.setStatus(carEntity.getStatus());
            carFound.setFuelType(carEntity.getFuelType());
            carFound.setTransmissionType(carEntity.getTransmissionType());
            carFound.setReserveCost(carEntity.getReserveCost());
            carFound.setPostDate(carEntity.getPostDate());

            CategoryEntity category = categoryService.getById(carEntity.getCategory().getId());
            carFound.setCategory(category);

            carRepository.save(carFound);
            return carFound;

        } catch (IOException e) {
            throw new RuntimeException("Error processing image JSON", e);
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

    public CarEntity create(CarEntity carEntity, String dir, List<MultipartFile> files) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> uploadedFiles = new ArrayList<>();

            if (files != null && !files.isEmpty()) {
                uploadedFiles = objectMapper.readValue(fileUploadService.uploadFiles(dir, files), List.class);
            }

            carEntity.setImages(objectMapper.writeValueAsString(uploadedFiles));
            return carRepository.save(carEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<CarEntity> searchCars(String search, Pageable pageable) {
        Specification<CarEntity> spec = CarSpecification.search(search);
        return carRepository.findAll(spec, pageable);
    }

    public Page<CarEntity> findByStartDateBetween(LocalDate start, LocalDate end, Pageable pageable){

        Page<CarEntity> cars = carRepository.findByPostDateBetween(start, end,pageable);

        if (cars.isEmpty()) {
            throw new ResourceNotFoundException("No cars found");
        }

        return cars;
    }

    public Page<CarEntity> findByBrand(String brand, Pageable pageable) throws ResourceNotFoundException {

        Page<CarEntity> cars = carRepository.findByBrand(brand, pageable);

        if (cars.isEmpty()) {
            //throw new RuntimeException("Cars not found");
            throw new ResourceNotFoundException("No cars found with brand: " + brand);
        }

        return cars;

    }

    public Page<CarEntity> findByLocationCity(String city, Pageable pageable) throws ResourceNotFoundException {

        Page<CarEntity> cars = carRepository.findByLocationCity(city, pageable);

        if (cars.isEmpty()) {
            //throw new RuntimeException("Cars not found");
            throw new ResourceNotFoundException("No cars found with location: " + city);
        }

        return cars;

    }

}
