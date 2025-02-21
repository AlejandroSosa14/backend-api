package com.digitalhouse.proyectofinal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.service.CarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("cars")
@Tag(name = "Car Controller", description = "API para la gestión de automóviles")
@Validated
public class CarController {

    private final CarService carService;

    @GetMapping
    @Operation(summary = "Obtener todos los autos", description = "Devuelve una lista de todos los autos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de autos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    public ResponseEntity<?> getAll() {

        try {
            return ResponseEntity.ok().body(carService.getAll());
        } catch (RuntimeException r) {

            Map<String,String> error = new HashMap<>();

            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

    }

    @GetMapping("/transmission/{transmission}")
    @Operation(summary = "Buscar autos por transmisión", description = "Filtra autos por tipo de transmisión (Manual o Automático).")
    @ApiResponse(responseCode = "200", description = "Lista de autos filtrada exitosamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\": 1, \"brand\": \"Toyota\", \"model\": \"Corolla\", \"transmission\": \"Automatic\"}]")))
    @ApiResponse(responseCode = "404", description = "No se encontraron autos con esa transmisión")
    public ResponseEntity<?> findByTransmission(
            @Parameter(description = "Tipo de transmisión a buscar", example = "Automatic") @PathVariable String transmission) {

        try {
            return ResponseEntity.ok().body(carService.findByTransmission(transmission));
        } catch (RuntimeException r) {

            Map<String,String> error = new HashMap<>();

            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auto por ID", description = "Devuelve los detalles de un automóvil específico según su ID.")
    @ApiResponse(responseCode = "200", description = "Auto encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    @ApiResponse(responseCode = "404", description = "Auto no encontrado")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID del auto a buscar", example = "1") @PathVariable Long id) {

        try {
            return ResponseEntity.ok().body(carService.getById(id));
        } catch (RuntimeException r) {

            Map<String,String> error = new HashMap<>();

            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    @Operation(summary = "Registrar un auto", description = "Guarda un nuevo auto en el sistema.")
    @ApiResponse(responseCode = "201", description = "Auto creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    @ApiResponse(responseCode = "400", description = "Error en la creación del auto")
    public ResponseEntity<?> create(@Valid @RequestBody CarEntity car) {

        CarEntity carEntitySave = carService.create(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carEntitySave);

    }

    @PutMapping("/{serial}")
    @Operation(summary = "Actualiza un auto", description = "Actualiza auto en el sistema.")
    @ApiResponse(responseCode = "200", description = "Auto actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarEntity.class)))
    @ApiResponse(responseCode = "400", description = "Error en la actualizacion del auto")
    public ResponseEntity<?> update(@PathVariable(name = "serial") String serialNumber, @Valid @RequestBody CarEntity car) {

        try {
            CarEntity carEntityUpdate = carService.update(serialNumber,car);
            return ResponseEntity.status(HttpStatus.OK).body(carEntityUpdate);
        }catch (RuntimeException r){

            Map<String,String> error = new HashMap<>();

            error.put("error",r.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un auto", description = "Elimina un auto del sistema según su ID.")
    @ApiResponse(responseCode = "200", description = "Auto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Auto no encontrado")
    public void deleteById(@Parameter(description = "ID del auto a eliminar", example = "1") @PathVariable Long id) {

        try {
            carService.deleteById(id);
            ResponseEntity.ok();
            // ResponseEntity.noContent().build();
        } catch (RuntimeException r) {
            ResponseEntity.notFound();
        }

    }

}
