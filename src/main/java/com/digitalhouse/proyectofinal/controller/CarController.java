package com.digitalhouse.proyectofinal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.service.CarService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("cars/")
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        try {
            return ResponseEntity.ok().body(carService.getAll());
        } catch (RuntimeException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r.getMessage());
        }

    }

    @GetMapping("transmission/{transmission}")
    public ResponseEntity<?> findByTransmission(@PathVariable String transmission) {

        try {
            return ResponseEntity.ok().body(carService.findByTransmission(transmission));
        } catch (RuntimeException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r.getMessage());
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(carService.getById(id));
        } catch (RuntimeException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> postMethodName(@RequestBody CarEntity car) {

        try {

            CarEntity carEntitySave = carService.create(car);

            return ResponseEntity.status(HttpStatus.CREATED).body(carEntitySave);
            
        } catch (RuntimeException r) {
            return ResponseEntity.badRequest().body(r.getMessage());
        }

    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {

        try {
            carService.deleteById(id);
            ResponseEntity.ok();
        } catch (RuntimeException r) {
            ResponseEntity.notFound();
        }

    }

}
